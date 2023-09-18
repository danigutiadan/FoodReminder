package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.BaseFragment
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui.screens.AddFoodScreen
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date


@AndroidEntryPoint
class AddFoodFragment : BaseFragment() {
    private val viewModel: AddFoodViewModel by viewModels()
    private val doClosePictureDialog = MutableStateFlow(false)


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel.getAllFoodTypes()

        return ComposeView(requireContext()).apply {
            setContent {
                val foodTypeState: Response<List<FoodType>> by viewModel.foodTypeList.collectAsState()
                val foodName: String by viewModel.foodName.collectAsState()
                val foodQuantity: String by viewModel.foodQuantity.collectAsState()
                val expiryDate: Date? by viewModel.expiryDate.collectAsState()
                val daysBeforeExpiration: String by viewModel.daysBeforeExpiration.collectAsState()
                val remoteImageUrl: String by viewModel.foodImageUrl.collectAsState()
                val isNameError: Boolean by viewModel.isNameError.collectAsState()
                val isQuantityError: Boolean by viewModel.isQuantityError.collectAsState()
                val isFoodTypeError: Boolean by viewModel.isFoodTypeError.collectAsState()
                val isExpiryDateError: Boolean by viewModel.isExpiryDateError.collectAsState()
                val isDaysBeforeExpirationError: Boolean by viewModel.isDaysBeforeExpirationError.collectAsState()
                val selectedFoodType: FoodType? by viewModel.foodType.collectAsState()
                val isFoodAddedSuccessfully: Boolean by viewModel.isFoodAddedSuccessfully.collectAsState()
                AddFoodScreen(
                    backClickListener = { activity?.onBackPressed() },
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
                    isFoodAddedSuccessfully = isFoodAddedSuccessfully
                )
            }
        }


    }

    private fun initiateScan() {
        // Dentro de tu función onCreate, onCreateView o donde sea apropiado
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea un código de barras")
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