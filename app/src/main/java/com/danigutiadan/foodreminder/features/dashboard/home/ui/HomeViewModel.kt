package com.danigutiadan.foodreminder.features.dashboard.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.features.food.domain.usecase.GetAllFoodUseCase
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.foodreminder.features.food.domain.usecase.DeleteFoodUseCase
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import com.danigutiadan.foodreminder.features.food_type.domain.usecases.InsertFoodTypeUseCase
import com.danigutiadan.foodreminder.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAllFoodUseCase: GetAllFoodUseCase,
    private val insertFoodTypeUseCase: InsertFoodTypeUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase,
    val preferences: Preferences
) : ViewModel() {

    private val _logoutState = MutableStateFlow<Response<Void>>(Response.Loading)
    val logoutState: StateFlow<Response<Void>> = _logoutState

    private val _foodListState = MutableStateFlow<List<FoodWithFoodType>>(mutableListOf())
    val foodListState: StateFlow<List<FoodWithFoodType>> = _foodListState

    private val _deleteFoodState = MutableStateFlow<Response<Unit>>(Response.Loading)
    val deleteFoodState: StateFlow<Response<Unit>> = _deleteFoodState

    fun getAllFood() {
        getAllFoodUseCase.execute()
            .onStart { _foodListState.value = mutableListOf() }
            .onEach { _foodListState.value = it }
            .launchIn(viewModelScope)
    }

    fun logout() {
        logoutUseCase.execute()
            .onStart { _logoutState.value = Response.Loading }
            .onEach { _logoutState.value = it }
            .launchIn(viewModelScope)
    }

    fun deleteFoodFromList(food: FoodWithFoodType) {
        deleteFoodUseCase.execute(food.food)
            .onStart { _deleteFoodState.value = Response.Loading }
            .onEach { _deleteFoodState.value = Response.EmptySuccess }
            .launchIn(viewModelScope)
        val mutableList: MutableList<FoodWithFoodType> = _foodListState.value.toMutableList()
        mutableList.remove(food)
        _foodListState.value = mutableList
    }



}