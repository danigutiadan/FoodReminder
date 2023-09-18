package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.edit_food.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.BaseFragment
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.edit_food.ui.screens.EditFoodScreen
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date

@AndroidEntryPoint
class EditFoodFragment : BaseFragment() {
    private var food: FoodInfo? = null
    private val viewModel: EditFoodViewModel by viewModels()
    private val doClosePictureDialog = MutableStateFlow(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //viewModel.getFoodById((activity as EditFoodActivity).getFoodId())
        getFoodInfo()
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
                EditFoodScreen(
                    backClickListener = { activity?.onBackPressed() },
                    barcodeClickListener = {
                        //initiateScan()
                    },
                    saveFoodListener = {
                        if(viewModel.allFieldsFilled())
                            viewModel.updateFood()
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
                    onTakePicture = { (activity as EditFoodActivity).takePicture(viewModel) },
                    onGetExistentPicture = {
                        (activity as EditFoodActivity).takeExistentPicture(
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
                    selectedFoodType = selectedFoodType
                )
            }
        }
    }

    private fun getFoodInfo() {
        food = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity?.intent?.getSerializableExtra("food")?.let { it as? FoodInfo }
        } else {
            activity?.intent?.extras?.getSerializable("food")?.let { it as? FoodInfo }
        }

        food?.let {
            viewModel.onFoodReceived(it)
        }
    }

}