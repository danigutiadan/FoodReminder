package com.danigutiadan.foodreminder.features.food_type.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.GetAllFoodTypesUseCase
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.InsertFoodTypeUseCase
import com.danigutiadan.foodreminder.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class FoodTypeViewModel @Inject constructor(
    private val insertFoodTypeUseCase: InsertFoodTypeUseCase,
    private val getAllFoodTypesUseCase: GetAllFoodTypesUseCase
): ViewModel() {

    private var _foodTypeState = MutableStateFlow<Response<List<FoodType>>>(Response.Loading)
    var foodTypeState: StateFlow<Response<List<FoodType>>> = _foodTypeState

    fun insertFoodType(foodType: FoodType) {
        insertFoodTypeUseCase.execute(foodType = foodType)
            .launchIn(viewModelScope)
    }

    fun getAllFoodTypes() {
        getAllFoodTypesUseCase.execute()
            .onStart { _foodTypeState.value = Response.Loading }
            .onEach { _foodTypeState.value = it }
            .launchIn(viewModelScope)
    }
}