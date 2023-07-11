package com.danigutiadan.foodreminder.features.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.databinding.ActivityDashboardBinding
import com.danigutiadan.foodreminder.features.add_food.ui.AddFoodTypeViewModel
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.ui.AddUserInfoViewModel
import com.danigutiadan.foodreminder.features.onboarding.ui.MY_PERMISSIONS_REQUEST_CAMERA
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CODE_IMAGE_PICKER
import com.danigutiadan.foodreminder.utils.ImageUtils
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var addFoodTypeViewModel: AddFoodTypeViewModel
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navController = findNavController(R.id.nav_host_fragment)
        setUpNavigation(binding)
    }


    private fun setUpNavigation(binding: ActivityDashboardBinding) {
        binding.bnvDashboard.setupWithNavController(navController)
    }

    fun takePicture(viewModel: AddFoodTypeViewModel) {
        addFoodTypeViewModel = viewModel
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
            addFoodTypeViewModel.imageUri = ImageUtils.takePictureFromCamera(this)
        }


    }

    fun takeExistentPicture(viewModel: AddFoodTypeViewModel) {
        addFoodTypeViewModel = viewModel
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)

    }

    private fun rotateBitmap(bitmap: Bitmap, imagePath: String): Bitmap {
        val exif = ExifInterface(imagePath)
        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            when (requestCode) {
                REQUEST_CAPTURE_IMAGE -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        addFoodTypeViewModel.imageUri
                    )
                   val rotatedBitmap = rotateBitmap(
                        bitmap,
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +
                                "/TakenFromCamera.jpg"
                    )
                    addFoodTypeViewModel.updateProfileBitmap(rotatedBitmap)
                    addFoodTypeViewModel.bottomSheetState.value =
                        BottomSheetState(initialValue = BottomSheetValue.Collapsed)

                }

                REQUEST_CODE_IMAGE_PICKER -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
                    addFoodTypeViewModel.updateProfileBitmap(bitmap)
                    addFoodTypeViewModel.bottomSheetState.value =
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
                    addFoodTypeViewModel.imageUri = ImageUtils.takePictureFromCamera(this)
                }
            }
        }
    }
}