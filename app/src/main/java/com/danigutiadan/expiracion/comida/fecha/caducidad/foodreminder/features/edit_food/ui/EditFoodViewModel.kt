package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.edit_food.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.FoodUtils
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui.DateBeforeExpirationWarning
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetFoodInfoByBarcodeUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.UpdateFoodUseCase
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
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
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

    private val _daysBeforeExpiration = MutableStateFlow(1)
    val daysBeforeExpiration: StateFlow<Int> = _daysBeforeExpiration

    private val _foodImageUrl = MutableStateFlow("")
    val foodImageUrl: StateFlow<String> = _foodImageUrl

    private val _foodInfo = MutableStateFlow<FoodInfo?>(null)
    val foodInfo: StateFlow<FoodInfo?> = _foodInfo

    private val _isFoodEditedSuccessfully = MutableStateFlow(false)
    val isFoodEditedSuccessfully: StateFlow<Boolean> = _isFoodEditedSuccessfully

    private val _foodTypeByBarcodeState =
        MutableStateFlow<Response<BarcodeFoodResponse?>>(Response.Loading)

    private val _isDaysBeforeNotificationEnabled = MutableStateFlow(false)
    val isDaysBeforeNotificationEnabled: StateFlow<Boolean> = _isDaysBeforeNotificationEnabled

    private val _isExpiryDateEarlierThanToday = MutableStateFlow(false)
    var isExpiryDateEarlierThanToday: StateFlow<Boolean> = _isExpiryDateEarlierThanToday

    private val _dateBeforeExpirationWarning: MutableStateFlow<DateBeforeExpirationWarning?> = MutableStateFlow(null)
    val dateBeforeExpirationWarning: StateFlow<DateBeforeExpirationWarning?> = _dateBeforeExpirationWarning


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
        _isDaysBeforeNotificationEnabled.value = true
        checkIfExpiryDateIsEarlierThanToday()
        _daysBeforeExpiration.value = 1
    }

    private fun checkIfExpiryDateIsEarlierThanToday() {
        _isExpiryDateEarlierThanToday.value =
            FoodUtils.isExpiryDateLaterThanToday(_expiryDate.value!!).not() ||
                    FoodUtils.isExpiryDateTheSameAsToday(_expiryDate.value!!) || FoodUtils.isExpiryDateTheSameAsTomorrow(_expiryDate.value!!)

        _dateBeforeExpirationWarning.value = when {
            FoodUtils.isExpiryDateTheSameAsToday(_expiryDate.value!!) -> DateBeforeExpirationWarning.EXPIRATES_TODAY
            FoodUtils.isExpiryDateLaterThanToday(_expiryDate.value!!).not() -> DateBeforeExpirationWarning.EXPIRATED
            FoodUtils.isExpiryDateTheSameAsTomorrow(_expiryDate.value!!) -> DateBeforeExpirationWarning.EXPIRATES_TOMORROW
            else -> null
        }
    }
    fun shouldShowNotification(): Boolean {
        return FoodUtils.isExpiryDateLaterThanToday(_expiryDate.value!!) &&
                !FoodUtils.isExpiryDateTheSameAsToday(_expiryDate.value!!) && !FoodUtils.isExpiryDateTheSameAsTomorrow(
            _expiryDate.value!!
        )
    }

    fun onDaysBeforeExpiration(daysBeforeExpired: Int) {
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
        val maximumDays = maximumDaysBeforeNotification()
        when  {
            _daysBeforeExpiration.value >= maximumDays -> _daysBeforeExpiration.value = maximumDays
            _daysBeforeExpiration.value < 1 -> _daysBeforeExpiration.value = 1
            else -> _daysBeforeExpiration.value += 1
        }
    }

    private fun maximumDaysBeforeNotification(): Int {
        val localDate1 = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val localDate2 = _expiryDate.value?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()

        return (ChronoUnit.DAYS.between(localDate1, localDate2) - 1).toInt()
    }

    fun onDaysBeforeExpirationMinusPressed() {
        when  {
            _daysBeforeExpiration.value <= 1 -> _daysBeforeExpiration.value = 1
            else -> _daysBeforeExpiration.value -= 1
        }
    }

    fun updateFood() {
        updateFoodUseCase.execute(
            id = _foodInfo.value?.food?.id,
            name = _foodName.value,
            quantity = _foodQuantity.value.toInt(),
            foodType = _foodType.value?.id!!,
            expiryDate = getCorrectExpiryDate(),
            daysBeforeExpiration = _daysBeforeExpiration.value,
            foodImageUrl = _foodImageUrl.value
        )
            .onStart { }
            .onEach {
                if(it is Response.EmptySuccess) {
                    _isFoodEditedSuccessfully.value = true
                }

            }.launchIn(viewModelScope)

    }

    fun getCorrectExpiryDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = _expiryDate.value!!
        val correctDate = Calendar.getInstance()
        correctDate.set(Calendar.DAY_OF_MONTH,  calendar.get(Calendar.DAY_OF_MONTH))
        correctDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        correctDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        return correctDate.time
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
            .and(_daysBeforeExpiration.value >= 1)
    }

    fun onFoodReceived(food: FoodInfo?) {
        if(food != null) {
            getAllFoodTypes()
            _foodInfo.value = food
            _foodImageUrl.value = food.food.foodImageUrl ?: ""
            _foodName.value = food.food.name
            _foodQuantity.value = food.food.quantity.toString()
            _foodType.value = food.foodType
            onExpiryDateSelected(food.food.expiryDate)
            _daysBeforeExpiration.value = food.food.daysBeforeExpirationNotification

        }

    }




}