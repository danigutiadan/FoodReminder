package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.BaseFragment
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.ads.randomNumber
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.ads.showInterstitial
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui.screens.AddFoodScreen
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.notifications.FoodNotification
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class AddFoodFragment : BaseFragment() {
    private val viewModel: AddFoodViewModel by viewModels()
    private val doClosePictureDialog = MutableStateFlow(false)
    @Inject
    lateinit var alarmManager: AlarmManager




    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel.getAllFoodTypes()
        createNotification()

        return ComposeView(requireContext()).apply {
            setContent {
                val foodTypeState: Response<List<FoodType>> by viewModel.foodTypeList.collectAsState()
                val foodName: String by viewModel.foodName.collectAsState()
                val foodQuantity: String by viewModel.foodQuantity.collectAsState()
                val expiryDate: Date? by viewModel.expiryDate.collectAsState()
                val daysBeforeExpiration: Int by viewModel.daysBeforeExpiration.collectAsState()
                val remoteImageUrl: String by viewModel.foodImageUrl.collectAsState()
                val isNameError: Boolean by viewModel.isNameError.collectAsState()
                val isQuantityError: Boolean by viewModel.isQuantityError.collectAsState()
                val isFoodTypeError: Boolean by viewModel.isFoodTypeError.collectAsState()
                val isExpiryDateError: Boolean by viewModel.isExpiryDateError.collectAsState()
                val isDaysBeforeExpirationError: Boolean by viewModel.isDaysBeforeExpirationError.collectAsState()
                val isExpiryDateEarlierThanToday: Boolean by viewModel.isExpiryDateEarlierThanToday.collectAsState()
                val selectedFoodType: FoodType? by viewModel.foodType.collectAsState()
                val isFoodAddedSuccessfully: Boolean by viewModel.isFoodAddedSuccessfully.collectAsState()
                val shouldShowInterstitialAd: Boolean by viewModel.shouldShowInterstitialAd.collectAsState()
                val shouldShowSuccessfullySnackBar: Boolean by viewModel.shouldShowSuccessfullySnackBar.collectAsState()
                val isDaysBeforeNotificationEnabled: Boolean by viewModel.isDaysBeforeNotificationEnabled.collectAsState()
                val dateBeforeExpirationWarning: DateBeforeExpirationWarning? by viewModel.dateBeforeExpirationWarning.collectAsState()
                AddFoodScreen(
                    backClickListener = {
                        activity?.onBackPressed()},
                    barcodeClickListener = {
                        initiateScan()
                    },
                    saveFoodListener = {
                        if(viewModel.allFieldsFilled())
                            viewModel.saveFood()
                    },
                    onExpiryDateSelected = {
                        viewModel.onExpiryDateSelected(it)
                    },
                    foodTypeListState = foodTypeState,
                    bottomSheetPictureDialogState = viewModel.bottomSheetState.collectAsState().value,
                    closePictureDialog = doClosePictureDialog.collectAsState().value,
                    doCloseDialog = {
                        doClosePictureDialog.value = false
                    },
                    onTakePicture = { (activity as AddFoodActivity).takePicture(viewModel) },
                    onGetExistentPicture = {
                        (activity as AddFoodActivity).takeExistentPicture(
                            viewModel
                        )
                    },
                    foodQuantity = foodQuantity,
                    foodName = foodName,
                    onFoodTypeSelected = {
                        viewModel.onFoodTypeSelected(it)
                    },
                    onFoodQuantityChanged = {
                        viewModel.onFoodQuantityChanged(it)
                    },
                    onFoodNameChanged = {
                        viewModel.onFoodNameChanged(it)
                    },
                    dateSelected = expiryDate,
                    daysBeforeExpiration = daysBeforeExpiration,
                    onDaysBeforeExpiration = {
                        viewModel.onDaysBeforeExpiration(it)
                    },
                    onQuantityPlusPressed = {
                        viewModel.onQuantityPlusPressed()
                    },
                    onQuantityMinusPressed = {
                        viewModel.onQuantityMinusPressed()

                    },
                    onDaysBeforeExpirationPlusPressed = {
                        viewModel.onDaysBeforeExpirationPlusPressed()

                    },
                    onDaysBeforeExpirationMinusPressed = {
                        viewModel.onDaysBeforeExpirationMinusPressed()

                    },
                    remoteImageUrl = remoteImageUrl,
                    isNameError = isNameError,
                    isQuantityError = isQuantityError,
                    isFoodTypeError = isFoodTypeError,
                    isExpiryDateError = isExpiryDateError,
                    isDaysBeforeExpirationError = isDaysBeforeExpirationError,
                    selectedFoodType = selectedFoodType,
                    isFoodAddedSuccessfully = isFoodAddedSuccessfully,
                    isExpiryDateEarlierThanToday = isExpiryDateEarlierThanToday,
                    shouldShowInterstitialAd = shouldShowInterstitialAd,
                    onInterstitialClosed = { viewModel.onInterstitialClosed() },
                    onSnackBarDisappeared = { viewModel.hideFoodAddedSuccessfullySnackBar()},
                    shouldShowSuccessfullySnackBar = shouldShowSuccessfullySnackBar,
                    isDaysBeforeNotificationEnabled = isDaysBeforeNotificationEnabled,
                    dateBeforeExpirationWarning = dateBeforeExpirationWarning
                )
            }
        }


    }

    private fun createNotification() {
        lifecycleScope.launch {
            viewModel.isFoodAddedSuccessfully.collect {isAddedSuccessfully ->
                if (isAddedSuccessfully) {
                    if(viewModel.shouldShowNotification()) {
                        val intent = Intent(context, FoodNotification::class.java)
                        val pendingIntent = PendingIntent.getBroadcast(context, viewModel.foodAddedId.value, intent, PendingIntent.FLAG_IMMUTABLE)

                        // Configura la alarma
                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + getFoodNotificationDate(), pendingIntent)

                    }

                }
            }
        }
    }

    private fun getFoodNotificationDate(): Long {
        val foodDaysBeforeExpirationNotification = viewModel.daysBeforeExpiration.value.toInt()
        val calendar = Calendar.getInstance()
        calendar.time = viewModel.getCorrectExpiryDate()
        calendar.add(Calendar.DAY_OF_YEAR, - foodDaysBeforeExpirationNotification)
        return calendar.timeInMillis - Calendar.getInstance().timeInMillis
    }

    private fun initiateScan() {
        // Dentro de tu función onCreate, onCreateView o donde sea apropiado
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt(context?.getString(R.string.scan_barcode))
        integrator.setCameraId(0) // Usa la cámara trasera
        integrator.setBeepEnabled(false) // Desactiva el sonido de escaneo
        integrator.setOrientationLocked(true) // Permite que la orientación cambie durante el escaneo
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntentIntegrator.REQUEST_CODE -> {
                val result = IntentIntegrator.parseActivityResult(resultCode, data)
                if (result != null && result.contents != null) {
                    viewModel.onFoodBarcodeScanned(result.contents)
                } else {
                    // El escaneo fue cancelado por el usuario
                }
            }
        }
    }


}