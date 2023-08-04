package com.danigutiadan.foodreminder.features.edit_food.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.foodreminder.features.food.domain.usecase.GetFoodInfoByBarcodeUseCase
import com.danigutiadan.foodreminder.features.food.domain.usecase.UpdateFoodUseCase
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.foodreminder.utils.Response
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditFoodViewModel @Inject constructor(
    private val getAllFoodTypesUseCase: GetAllFoodTypesUseCase,
    private val getFoodInfoByBarcode: GetFoodInfoByBarcodeUseCase,
    private val updateFoodUseCase: UpdateFoodUseCase
) : ViewModel() {


    private val _isDaysBeforeExpirationError = MutableStateFlow(false)
    var isDaysBeforeExpirationError: StateFlow<Boolean> = _isDaysBeforeExpirationError

    private val _isExpiryDateError = MutableStateFlow(false)
    var isExpiryDateError: StateFlow<Boolean> = _isExpiryDateError

    private val _isFoodTypeError = MutableStateFlow(false)
    var isFoodTypeError: StateFlow<Boolean> = _isFoodTypeError


    private val _isQuantityError = MutableStateFlow(false)
    var isQuantityError: StateFlow<Boolean> = _isQuantityError

    private val _isNameError = MutableStateFlow(false)
    var isNameError: StateFlow<Boolean> = _isNameError

    val bottomSheetState = MutableStateFlow(BottomSheetState(BottomSheetValue.Collapsed))
    var imageUri: Uri? = null

    private val _foodTypeList = MutableStateFlow<Response<List<FoodType>>>(Response.Loading)
    val foodTypeList: StateFlow<Response<List<FoodType>>> = _foodTypeList

    private val _foodName = MutableStateFlow("")
    val foodName: StateFlow<String> = _foodName

    private val _foodQuantity = MutableStateFlow("0")
    val foodQuantity: StateFlow<String> = _foodQuantity

    private val _expiryDate = MutableStateFlow<Date?>(null)
    val expiryDate: StateFlow<Date?> = _expiryDate

    private val _foodType = MutableStateFlow<FoodType?>(null)
    val foodType: StateFlow<FoodType?> = _foodType

    private val _daysBeforeExpiration = MutableStateFlow("0")
    val daysBeforeExpiration: StateFlow<String> = _daysBeforeExpiration

    private val _foodImageUrl = MutableStateFlow("")
    val foodImageUrl: StateFlow<String> = _foodImageUrl

    private val _foodInfo = MutableStateFlow<FoodInfo?>(null)
    val foodInfo: StateFlow<FoodInfo?> = _foodInfo

    private val _foodTypeByBarcodeState =
        MutableStateFlow<Response<BarcodeFoodResponse?>>(Response.Loading)

    fun getAllFoodTypes() {
        getAllFoodTypesUseCase.execute()
            .onEach { _foodTypeList.value = Response.Loading }
            .onEach { _foodTypeList.value = it }
            .launchIn(viewModelScope)
    }

    fun updateProfileBitmap(filePath: String? = null) {
        _foodImageUrl.value = filePath ?: ""
    }

    fun onFoodQuantityChanged(quantity: String) {
        _foodQuantity.value = quantity
    }

    fun onFoodNameChanged(name: String) {
        _foodName.value = name
    }

    fun onFoodTypeSelected(foodType: FoodType) {
        _foodType.value = foodType
    }

    fun onExpiryDateSelected(date: Date) {
        _expiryDate.value = date
    }

    fun onFoodBarcodeScanned(barcode: String) {
        viewModelScope.launch {
            _foodTypeByBarcodeState.value = Response.Loading
            getFoodInfoByBarcode.execute(barcode)
                .collect { response ->
                    _foodTypeByBarcodeState.value = response

                    if (response is Response.Success) {
                        if (response.data?.products?.isNotEmpty() == true) {
                            _foodName.value = response.data.products?.first()?.name.toString()
                            _foodImageUrl.value = response.data.products?.first()?.imageUrl ?: ""
                        }
                    }
                }
        }
    }


    fun onDaysBeforeExpiration(daysBeforeExpired: String) {
        _daysBeforeExpiration.value = daysBeforeExpired
    }

    fun onQuantityPlusPressed() {
        _foodQuantity.value =
            if (_foodQuantity.value.isEmpty()) "1" else (_foodQuantity.value.toInt() + 1).toString()
    }

    fun onQuantityMinusPressed() {
        _foodQuantity.value =
            if (_foodQuantity.value.isEmpty()) "" else if (_foodQuantity.value == "0") "0" else (_foodQuantity.value.toInt() - 1).toString()
    }

    fun onDaysBeforeExpirationPlusPressed() {
        _daysBeforeExpiration.value =
            if (_daysBeforeExpiration.value.isEmpty()) "1" else (_daysBeforeExpiration.value.toInt() + 1).toString()
    }

    fun onDaysBeforeExpirationMinusPressed() {
        _daysBeforeExpiration.value =
            if (_daysBeforeExpiration.value.isEmpty()) "" else if (_daysBeforeExpiration.value == "0") "0" else (_daysBeforeExpiration.value.toInt() - 1).toString()

    }

    fun updateFood() {
        updateFoodUseCase.execute(
            id = _foodInfo.value?.food?.id,
            name = _foodName.value,
            quantity = _foodQuantity.value.toInt(),
            foodType = _foodType.value?.id!!,
            expiryDate = _expiryDate.value!!,
            daysBeforeExpiration = _daysBeforeExpiration.value.toInt(),
            foodImageUrl = _foodImageUrl.value
        )
            .onStart { }
            .onEach {
            }.launchIn(viewModelScope)

    }

    fun allFieldsFilled(): Boolean {
        _isNameError.value = _foodName.value.isBlank()
        _isQuantityError.value = _foodQuantity.value.toInt() < 1
        _isFoodTypeError.value = _foodType.value?.id == null
        _isExpiryDateError.value = _expiryDate.value == null
        _isDaysBeforeExpirationError.value = _daysBeforeExpiration.value.toInt() < 1

        return _foodName.value.isNotBlank().and(_foodQuantity.value.toInt() >= 1)
            .and(_foodType.value?.id != null)
            .and(_expiryDate.value != null)
            .and(_daysBeforeExpiration.value.toInt() >= 1)
    }

    fun onFoodReceived(food: FoodInfo?) {
        if(food != null) {
            getAllFoodTypes()
            _foodInfo.value = food
            _foodImageUrl.value = food.food.foodImageUrl ?: ""
            _foodName.value = food.food.name
            _foodQuantity.value = food.food.quantity.toString()
            _foodType.value = food.foodType
            _expiryDate.value = food.food.expiryDate
            _daysBeforeExpiration.value = food.food.daysBeforeExpirationNotification.toString()

        }

    }




}