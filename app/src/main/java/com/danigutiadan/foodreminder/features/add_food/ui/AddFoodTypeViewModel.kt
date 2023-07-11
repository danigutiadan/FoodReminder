package com.danigutiadan.foodreminder.features.add_food.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddFoodTypeViewModel @Inject constructor(
    private val getAllFoodTypesUseCase: GetAllFoodTypesUseCase
) : ViewModel() {


    val bottomSheetState = MutableStateFlow(BottomSheetState(BottomSheetValue.Collapsed))
    var imageUri: Uri? = null

    private val _profileBitmap = MutableStateFlow<Bitmap?>(null)
    val profileBitmap: StateFlow<Bitmap?> = _profileBitmap

    private val _foodTypeList = MutableStateFlow<Response<List<FoodType>>>(Response.Loading)
    val foodTypeList: StateFlow<Response<List<FoodType>>> = _foodTypeList

    private val _foodName = MutableStateFlow("")
    val foodName: StateFlow<String> = _foodName

    private val _foodQuantity = MutableStateFlow("0")
    val foodQuantity: StateFlow<String> = _foodQuantity

    private val _selectedDate = MutableStateFlow<Date?>(null)
    val selectedDate: StateFlow<Date?> = _selectedDate

    private val _foodType = MutableStateFlow<FoodType?>(null)
    val foodType: StateFlow<FoodType?> = _foodType

    private val _daysBeforeExpiration = MutableStateFlow("0")
    val daysBeforeExpiration: StateFlow<String> = _daysBeforeExpiration

    fun getAllFoodTypes() {
        getAllFoodTypesUseCase.execute()
            .onEach { _foodTypeList.value = Response.Loading }
            .onEach { _foodTypeList.value = it }
            .launchIn(viewModelScope)
    }

    fun updateProfileBitmap(bitmap: Bitmap) {
        _profileBitmap.value = bitmap
        //uploadUserImageUseCase.execute(bitmap)
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
        _selectedDate.value = date
    }

    fun onFoodBarcodeScanned(barcode: String) {
        //Getfoodbybarcode
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

}