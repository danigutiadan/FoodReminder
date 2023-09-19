package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetFoodInfoByBarcodeUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.SaveFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val getAllFoodTypesUseCase: GetAllFoodTypesUseCase,
    private val getFoodInfoByBarcode: GetFoodInfoByBarcodeUseCase,
    private val saveFoodUseCase: SaveFoodUseCase
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

    private val _isExpiryDateEarlierThanToday = MutableStateFlow(false)
    var isExpiryDateEarlierThanToday: StateFlow<Boolean> = _isExpiryDateEarlierThanToday

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

    private val _foodTypeByBarcodeState =
        MutableStateFlow<Response<BarcodeFoodResponse?>>(Response.Loading)

    private val _isFoodAddedSuccessfully = MutableStateFlow(false)
    val isFoodAddedSuccessfully: StateFlow<Boolean> = _isFoodAddedSuccessfully

    private val _foodAddedId = MutableStateFlow(0)
    val foodAddedId: StateFlow<Int> = _foodAddedId

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
        checkIfExpiryDateIsEarlierThanToday()
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

    fun saveFood() {
        saveFoodUseCase.execute(
            name = _foodName.value,
            quantity = _foodQuantity.value.toInt(),
            foodType = _foodType.value?.id!!,
            expiryDate = getCorrectExpiryDate(),
            daysBeforeExpiration = if(_daysBeforeExpiration.value.isNotBlank())_daysBeforeExpiration.value.toInt() else 0,
            foodImageUrl = _foodImageUrl.value
        )
            .onStart { }
            .onEach { response ->
                if (response is Response.Success) {
                    _foodAddedId.value = response.data.toInt()
                    _isFoodAddedSuccessfully.value = true
                    resetAllFields()
                }
            }.launchIn(viewModelScope)

    }

    fun getCorrectExpiryDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = _expiryDate.value!!
        val correctDate = Calendar.getInstance()
        correctDate.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
        correctDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        correctDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        return correctDate.time
    }

    private fun resetAllFields() {
        _foodName.value = ""
        _foodQuantity.value = "0"
        _foodType.value = null
        _expiryDate.value = null
        _daysBeforeExpiration.value = "0"
        _foodImageUrl.value = ""
        _isExpiryDateEarlierThanToday.value = false
        android.os.Handler().postDelayed( {
          _isFoodAddedSuccessfully.value = false
        }, 5000)



    }

    private fun isExpiryDateLaterThanToday(): Boolean {
        return Date().before(_expiryDate.value)
    }

    fun isExpiryDateLaterThanTodayInFragment(): Boolean {
        return Date().before(expiryDate.value)
    }

    private fun isExpiryDateTheSameAsToday(): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = Date()

        val cal2 = Calendar.getInstance()
        cal2.time = _expiryDate.value!!

        val sameDay = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
        val sameMonth = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
        val sameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)

        return sameDay && sameMonth && sameYear
    }

    private fun checkIfExpiryDateIsEarlierThanToday() {
        _isExpiryDateEarlierThanToday.value =
            isExpiryDateLaterThanToday().not() || isExpiryDateTheSameAsToday()
    }


    fun allFieldsFilled(): Boolean {
        _isNameError.value = _foodName.value.isBlank()
        _isQuantityError.value = _foodQuantity.value.toInt() < 1
        _isFoodTypeError.value = _foodType.value?.id == null
        _isExpiryDateError.value = _expiryDate.value == null
        if ((_isExpiryDateEarlierThanToday.value).not()) {
            _isDaysBeforeExpirationError.value = _daysBeforeExpiration.value.toInt() < 1
        }

        return _foodName.value.isNotBlank()
            .and(_foodQuantity.value.toInt() >= 1)
            .and(_foodType.value?.id != null)
            .and(_expiryDate.value != null)
            .let {
                if ((_isExpiryDateEarlierThanToday.value).not()) {
                    it.and(_daysBeforeExpiration.value.toInt() >= 1)
                } else {
                    it
                }
            }

    }
}
