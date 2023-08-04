package com.danigutiadan.foodreminder.features.dashboard.home.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.features.food.domain.usecase.GetAllFoodUseCase
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.foodreminder.features.food.domain.usecase.DeleteFoodUseCase
import com.danigutiadan.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.foodreminder.features.food.domain.usecase.GetFoodWithFiltersUseCase
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.InsertFoodTypeUseCase
import com.danigutiadan.foodreminder.utils.Response
import com.danigutiadan.foodreminder.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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


    fun getAllFood() {
        getAllFoodUseCase.execute()
            .onStart { _foodListState.value = mutableListOf() }
            .onEach { _foodListState.value = it }
            .launchIn(viewModelScope)
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
                manageFoodFiltersText()
            }
            .launchIn(viewModelScope)
    }

    private fun manageFoodFiltersText() {
        var filtersCount: Int = if (_foodStatusFilterState.value != null) {
            1
        } else {
            0
        }
            if (_foodTypeFilterState.value != null) {
                filtersCount += 1
            }

            when (filtersCount) {
                1 -> {
                    _foodFiltersTextState.value = "Filtros: 1"
                }

                2 -> {
                    _foodFiltersTextState.value = "Filtros: 2"
                }

                else -> {
                    _foodFiltersTextState.value = "Filtrar"
                }

        }
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