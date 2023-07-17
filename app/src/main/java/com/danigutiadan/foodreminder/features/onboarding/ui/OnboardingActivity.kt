package com.danigutiadan.foodreminder.features.onboarding.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danigutiadan.foodreminder.BaseActivity
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.features.dashboard.DashboardActivity
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.ui.AddUserInfoViewModel
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.ui.screens.AddUserInfoScreen
import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.features.onboarding.navigation.OnboardingNavigationKeys.Routes.ADD_USER_INFO
import com.danigutiadan.foodreminder.features.onboarding.navigation.OnboardingNavigationKeys.Routes.SIGN_IN
import com.danigutiadan.foodreminder.features.onboarding.navigation.OnboardingNavigationKeys.Routes.SIGN_UP
import com.danigutiadan.foodreminder.features.onboarding.navigation.navigateToAddUserInfo
import com.danigutiadan.foodreminder.features.onboarding.navigation.navigateToSignUp
import com.danigutiadan.foodreminder.features.onboarding.signin.ui.screens.SignInScreen
import com.danigutiadan.foodreminder.features.onboarding.signup.ui.screens.SignUpScreen
import com.danigutiadan.foodreminder.ui.theme.FoodReminderTheme
import com.danigutiadan.foodreminder.utils.ImageUtils
import com.danigutiadan.foodreminder.utils.Response
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date


const val REQUEST_CODE_IMAGE_PICKER = 100
const val REQUEST_CAPTURE_IMAGE = 101
const val MY_PERMISSIONS_REQUEST_CAMERA = 102
const val READ_EXTERNAL_STORAGE_PERMISSION_CODE = 104
private val bottomSheetState = MutableStateFlow(BottomSheetState(BottomSheetValue.Collapsed))
private val doClosePictureDialog = MutableStateFlow(false)

@AndroidEntryPoint
class OnboardingActivity : BaseActivity() {
    private var currentTimeMillis = ""
    private var navController: NavHostController? = null
    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private val addUserInfoViewModel: AddUserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.color.transparent)
        setContent {

            FoodReminderTheme {
                Onboarding(onboardingViewModel)
                setObservers()
            }
        }
    }

    override fun onBackPressed() {
        if (bottomSheetState.value.visible) {
            doClosePictureDialog.value = true
        } else {
            super.onBackPressed()
        }
    }

    @Composable
    private fun Onboarding(viewModel: OnboardingViewModel) {
        navController = rememberNavController()
        NavHost(navController!!, startDestination = SIGN_IN) {
            composable(route = SIGN_IN) {
                SignInDestination(
                    viewModel,
                    goToSignup = { navigateToSignUp(navController!!) },
                    { navigateToAddUserInfo(navController!!) })
            }
            composable(route = SIGN_UP) {
                SignUpDestination(viewModel)
            }

            composable(route = ADD_USER_INFO) {
                AddUserInfoDestination(
                    viewModel = addUserInfoViewModel,
                    state = bottomSheetState.collectAsState().value,
                    closePictureDialog = doClosePictureDialog.collectAsState().value
                ) {
                    doClosePictureDialog.value = false
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            when (requestCode) {
                REQUEST_CAPTURE_IMAGE -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        addUserInfoViewModel.imageUri
                    )
                    addUserInfoViewModel.updateProfileBitmap(bitmap)
                    bottomSheetState.value =
                        BottomSheetState(initialValue = BottomSheetValue.Collapsed)

                }

                REQUEST_CODE_IMAGE_PICKER -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
                    addUserInfoViewModel.updateProfileBitmap(bitmap)
                    bottomSheetState.value =
                        BottomSheetState(initialValue = BottomSheetValue.Collapsed)
                }

                else -> {}
            }
        else
            return
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addUserInfoViewModel.imageUri =
                        ImageUtils.takePictureFromCamera(this, currentTimeMillis)
                }
            }
        }
    }

    private fun controlEmailLoginState(response: Response<FirebaseUser>) {
        when (response) {
            is Response.Success -> {
                Log.d("Datos:", response.data.toString())
                //navigateToSignUp(navController!!)
                manageLoginSuccess()
            }

            else -> {}
        }
    }

    private fun manageLoginSuccess() {
        if (preferences.user?.isRegisterCompleted == false)
            navigateToAddUserInfo(navController!!)
        else
            navigateToDashboard(true)
    }


    private fun navigateToDashboard(clearStack: Boolean) {
        val intent = Intent(this, DashboardActivity::class.java)
        if (clearStack) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun controlEmailRegisterState(response: Response<FirebaseUser>) {
        when (response) {
            is Response.Success -> {
                Log.d("Datos:", response.data.toString())
                //navigateToSignIn(navController!!)
                navController!!.navigate(route = SIGN_IN, navOptions = NavOptions.Builder().apply {
                    setPopUpTo(SIGN_IN, true)
                }.build())
            }

            else -> {}
        }
    }

    private fun setObservers() {
        onboardingViewModel.apply {
            emailSignInState.onEach(::controlEmailLoginState).launchIn(lifecycleScope)
            emailRegisterState.onEach(::controlEmailRegisterState).launchIn(lifecycleScope)
        }

        addUserInfoViewModel.apply {
            addUserInfoState.onEach(::controlAddUserInfo).launchIn(lifecycleScope)
        }
    }

    private fun controlAddUserInfo(state: Response<UserInfo>) {
        when (state) {
            is Response.Success -> {
                navigateToDashboard(true)
            }

            is Response.Loading -> {}

            is Response.EmptySuccess -> TODO()
            is Response.Error -> TODO()
        }
    }

    @Composable
    private fun SignInDestination(
        viewModel: OnboardingViewModel,
        goToSignup: () -> Unit,
        goToAddUserInfo: () -> Unit
    ) {
        val email: String by viewModel.email.collectAsState()
        val password: String by viewModel.password.collectAsState()
        val isButtonEnabled: Boolean by viewModel.buttonEnabled.collectAsState()
        val signInState: Response<FirebaseUser> by viewModel.emailSignInState.collectAsState()

        SignInScreen(
            email = email,
            password = password,
            isButtonEnabled = isButtonEnabled,
            signInState = signInState,
            onLoginChanged = { emailSignIn, passwordSignIn ->
                viewModel.onLoginChanged(email = emailSignIn, password = passwordSignIn)
            },
            resetValues = { viewModel.resetValues() },
            goToSignUp = goToSignup,
            goToAddUserInfo = goToAddUserInfo,
            signInWithEmail = { viewModel.signInWithEmail() }
        )
    }

    @Composable
    private fun SignUpDestination(viewModel: OnboardingViewModel) {
        val email: String by viewModel.email.collectAsState()
        val password: String by viewModel.password.collectAsState()
        val repeatPassword: String by viewModel.repeatPassword.collectAsState()
        val termsChecked: Boolean by viewModel.termsChecked.collectAsState()
        val isButtonEnabled: Boolean by viewModel.buttonEnabled.collectAsState()

        SignUpScreen(
            email = email,
            password = password,
            repeatPassword = repeatPassword,
            termsChecked = termsChecked,
            isButtonEnabled = isButtonEnabled,
            onSignupChanged = { emailSignUp, passwordSignUp, repeatPasswordSignUp, termsCheckedSignUp ->
                viewModel.onSignUpChanged(
                    emailSignUp,
                    passwordSignUp,
                    repeatPasswordSignUp,
                    termsCheckedSignUp
                )
            },
            onTermsChecked = { viewModel.onTermsChecked(it) },
            onLoginButtonClicked = { viewModel.registerWithEmail() }
        )
    }

    @Composable
    private fun AddUserInfoDestination(
        viewModel: AddUserInfoViewModel,
        state: BottomSheetState,
        closePictureDialog: Boolean,
        doCloseDialog: () -> Unit
    ) {
        val name: String by viewModel.name.collectAsState()
        val lastName: String by viewModel.lastName.collectAsState()
        val termsChecked: Boolean by viewModel.addUserInfoTermsChecked.collectAsState()
        val addUserInfoButtonEnabled: Boolean by viewModel.submitButtonEnabled.collectAsState()
        val profileBitmap: Bitmap? by viewModel.profileBitmap.collectAsState()
        val birth: Date? by viewModel.birth.collectAsState()
        AddUserInfoScreen(
            bottomSheetPictureDialogState = state,
            closePictureDialog = closePictureDialog,
            doCloseDialog = doCloseDialog,
            name = name,
            lastName = lastName,
            profileBitmap = profileBitmap,
            birth = birth,
            termsChecked = termsChecked,
            addUserInfoButtonEnabled = addUserInfoButtonEnabled,
            onNameChanged = { viewModel.onNameChanged(it) },
            onLastNameChanged = { viewModel.onLastNameChanged(it) },
            onBirthChanged = { viewModel.onBirthChanged(it) },
            onTermsChecked = { viewModel.onAddUserInfoTermsChecked(it) },
            addUserInfoClick = { viewModel.addUserInfo() },
            onTakePicture = { takePicture(viewModel) },
            onGetExistentPicture = { takeExistentPicture() }

        )
    }

    private fun takePicture(viewModel: AddUserInfoViewModel) {
        currentTimeMillis = Date().time.toString()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Ask for the permission
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA
            )
        } else {
            viewModel.imageUri = ImageUtils.takePictureFromCamera(this, currentTimeMillis)
        }


    }

    private fun takeExistentPicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)

    }


}