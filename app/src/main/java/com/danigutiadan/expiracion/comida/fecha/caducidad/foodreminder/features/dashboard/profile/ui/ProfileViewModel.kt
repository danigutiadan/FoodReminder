package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.Preferences
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.usecases.LogoutUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    val preferences: Preferences
) : ViewModel() {

    private val _logoutState = MutableStateFlow<Response<Void>>(Response.Loading)
    val logoutState: StateFlow<Response<Void>> = _logoutState

    fun logout() {
        logoutUseCase.execute()
            .onStart { _logoutState.value = Response.Loading }
            .onEach { _logoutState.value = it }
            .launchIn(viewModelScope)
    }

}