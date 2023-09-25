package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.Preferences
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.DeleteFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetAllFoodUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.GetFoodWithFiltersUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase.UpdateFoodStatusUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.usecases.InsertFoodTypeUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAllFoodUseCase: GetAllFoodUseCase,
    private val insertFoodTypeUseCase: InsertFoodTypeUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase,
    private val getFoodWithFiltersUseCase: GetFoodWithFiltersUseCase,
    private val getAllFoodTypesUseCase: GetAllFoodTypesUseCase,
    val preferences: Preferences,
    private val updateFoodStatusUseCase: UpdateFoodStatusUseCase
) : ViewModel() {


    private val _searchFoodInputState = MutableStateFlow("")
    val searchFoodInputState: StateFlow<String> = _searchFoodInputState

    private val _logoutState = MutableStateFlow<Response<Void>>(Response.Loading)
    val logoutState: StateFlow<Response<Void>> = _logoutState

    private val _foodListState = MutableStateFlow<List<FoodInfo>>(mutableListOf())
    val foodListState: StateFlow<List<FoodInfo>> = _foodListState

    private val _deleteFoodState = MutableStateFlow<Response<Unit>>(Response.Loading)
    val deleteFoodState: StateFlow<Response<Unit>> = _deleteFoodState

    private val _foodStatusFilterState = MutableStateFlow<FoodStatus?>(null)
    val foodStatusFilterState: StateFlow<FoodStatus?> = _foodStatusFilterState

    private val _foodTypeFilterState = MutableStateFlow<FoodType?>(null)
    val foodTypeFilterState: StateFlow<FoodType?> = _foodTypeFilterState

    private val _foodTypeListState = MutableStateFlow<List<FoodType>>(mutableListOf())
    val foodTypeListState: StateFlow<List<FoodType>> = _foodTypeListState

    private val _foodFiltersTextState = MutableStateFlow("Filtros")
    val foodFiltersTextState: StateFlow<String> = _foodFiltersTextState

    private val _foodOrderState = MutableStateFlow<FoodOrder?>(null)
    val foodOrderState: StateFlow<FoodOrder?> = _foodOrderState

    private val _isAllFoodUpdated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAllFoodUpdated: StateFlow<Boolean> = _isAllFoodUpdated


    fun getAllFood() {
        getAllFoodUseCase.execute()
            .onStart { _foodListState.value = mutableListOf() }
            .onEach { _foodListState.value = it }
            .launchIn(viewModelScope)
    }
    
    fun getAllFoodForUpdate() {
        getAllFoodUseCase.execute()
            .onStart { _foodListState.value = mutableListOf() }
            .onEach { foodList ->
                if(foodList.isNotEmpty()) {
                    viewModelScope.launch {
                        updateFoodListState(foodList)
                    }
                 } else {
                    _isAllFoodUpdated.value = true
                }
                }

            .launchIn(viewModelScope)
    }

     private fun updateFoodListState(foodList: List<FoodInfo>) {
        updateFoodStatusUseCase.execute(foodList)
            .onStart {
                _isAllFoodUpdated.value = false
            }
            .onEach { response ->
                if(response is Response.EmptySuccess) {
                    _isAllFoodUpdated.value = true
            } }.launchIn(viewModelScope)
    }

    fun getFoodTypeList() {
        getAllFoodTypesUseCase.execute()
            .onStart { _foodTypeListState.value = mutableListOf() }
            .onEach {
                if (it is Response.Success)
                    _foodTypeListState.value = it.data
            }
            .launchIn(viewModelScope)
    }

    fun getFoodWithFilters() {
        getFoodWithFiltersUseCase.execute(
            name = _searchFoodInputState.value,
            foodStatus = _foodStatusFilterState.value?.value,
            foodType = _foodTypeFilterState.value,
            foodOrder = _foodOrderState.value
        )
            .onStart { }
            .onEach {
                Log.d("Listado: ", it.toString())
                _foodListState.value = it
            }
            .launchIn(viewModelScope)
    }

    fun logout() {
        logoutUseCase.execute()
            .onStart { _logoutState.value = Response.Loading }
            .onEach { _logoutState.value = it }
            .launchIn(viewModelScope)
    }

    fun deleteFoodFromList(food: FoodInfo) {
        deleteFoodUseCase.execute(food.food)
            .onStart { _deleteFoodState.value = Response.Loading }
            .onEach { _deleteFoodState.value = Response.EmptySuccess }
            .launchIn(viewModelScope)
        val mutableList: MutableList<FoodInfo> = _foodListState.value.toMutableList()
        mutableList.remove(food)
        _foodListState.value = mutableList
    }

    fun onSearchFoodChanged(input: String) {
        _searchFoodInputState.value = input
        getFoodWithFilters()

    }

    fun updateFoodStatusFilter(foodStatus: FoodStatus?) {
        _foodStatusFilterState.value = foodStatus
    }

    fun updateFoodTypeFilter(foodType: FoodType?) {
        _foodTypeFilterState.value = foodType
    }

    fun updateFoodOrder(foodOrder: FoodOrder?) {
        _foodOrderState.value = foodOrder
        getFoodWithFilters()
    }


}