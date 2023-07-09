package com.danigutiadan.foodreminder.features.dashboard.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.features.dashboard.home.domain.usecases.GetAllFoodUseCase
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getAllFoodUseCase: GetAllFoodUseCase,
    val preferences: Preferences
) : ViewModel() {

    private val _logoutState = MutableStateFlow<Result<Void>>(Result.Loading)
    val logoutState: StateFlow<Result<Void>> = _logoutState

    private val _getAllFoodState = MutableStateFlow<List<Food>>(mutableListOf())
    val getAllFoodState: StateFlow<List<Food>> = _getAllFoodState

    fun getAllFood() {
        getAllFoodUseCase.execute()
            .onStart { _getAllFoodState.value = mutableListOf() }
            .onEach { _getAllFoodState.value = it }
            .launchIn(viewModelScope)
    }

    fun logout() {
        logoutUseCase.execute()
            .onStart { _logoutState.value = Result.Loading }
            .onEach { _logoutState.value = it }
            .launchIn(viewModelScope)
    }

}