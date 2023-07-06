package com.danigutiadan.foodreminder.features.dashboard.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.foodreminder.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    val preferences: Preferences
) : ViewModel() {

    private val _logoutState = MutableStateFlow<Result<Void>>(Result.Loading)
    val logoutState: StateFlow<Result<Void>> = _logoutState

    fun logout() {
        logoutUseCase.execute()
            .onStart { _logoutState.value = Result.Loading }
            .onEach { _logoutState.value = it }
            .launchIn(viewModelScope)
    }

}