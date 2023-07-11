package com.danigutiadan.foodreminder.features.add_food.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.getValue
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.danigutiadan.foodreminder.features.add_food.ui.screens.AddFoodScreen
import com.danigutiadan.foodreminder.features.dashboard.DashboardActivity
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import com.google.zxing.integration.android.IntentIntegrator


@AndroidEntryPoint
class AddFoodFragment : Fragment() {
    private val viewModel: AddFoodTypeViewModel by viewModels()
    private val doClosePictureDialog = MutableStateFlow(false)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel.getAllFoodTypes()

        return ComposeView(requireContext()).apply {
            setContent {
                val foodTypeState: Response<List<FoodType>> by viewModel.foodTypeList.collectAsState()
                val profileBitmap: Bitmap? by viewModel.profileBitmap.collectAsState()
                val foodName: String by viewModel.foodName.collectAsState()
                val foodQuantity: String by viewModel.foodQuantity.collectAsState()
                val expiryDate: Date? by viewModel.selectedDate.collectAsState()
                val daysBeforeExpiration: String by viewModel.daysBeforeExpiration.collectAsState()
                AddFoodScreen(
                    backClickListener = { activity?.onBackPressed() },
                    barcodeClickListener = {
                        initiateScan()
                    },
                    saveFoodListener = {},
                    onExpiryDateSelected = {
                        viewModel.onExpiryDateSelected(it)
                    },
                    foodTypeListState = foodTypeState,
                    foodBitmap = profileBitmap,
                    bottomSheetPictureDialogState = viewModel.bottomSheetState.collectAsState().value,
                    closePictureDialog = doClosePictureDialog.collectAsState().value,
                    doCloseDialog = {
                        doClosePictureDialog.value = false
                    },
                    onTakePicture = { (activity as DashboardActivity).takePicture(viewModel) },
                    onGetExistentPicture = {
                        (activity as DashboardActivity).takeExistentPicture(
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
                    val scannedBarcode: String = result.contents
                    viewModel.onFoodBarcodeScanned(result.contents)
                } else {
                    // El escaneo fue cancelado por el usuario
                }
            }
        }
    }


}