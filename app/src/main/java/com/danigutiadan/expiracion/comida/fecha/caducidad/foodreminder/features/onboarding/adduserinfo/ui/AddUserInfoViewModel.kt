package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.ui

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.Preferences
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.domain.usecases.AddUserInfoUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.domain.usecases.UploadUserImageUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.usecases.GetUserInfoUseCase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddUserInfoViewModel @Inject constructor(
    private val addUserInfoUseCase: AddUserInfoUseCase,
    private val uploadUserImageUseCase: UploadUserImageUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    val preferences: Preferences
) : ViewModel() {
    var imageUri: Uri? = null

    private val _name = MutableStateFlow(preferences.user?.name ?: "")
    val name: StateFlow<String> = _name

    private val _lastName = MutableStateFlow(preferences.user?.lastName ?: "")
    val lastName: StateFlow<String> = _lastName

    private val _birth: MutableStateFlow<Date?> = MutableStateFlow(null)
    val birth: StateFlow<Date?> = _birth

    private val _profileBitmap = MutableStateFlow<Bitmap?>(null)
    val profileBitmap: StateFlow<Bitmap?> = _profileBitmap

    private val _submitButtonEnabled = MutableStateFlow<Boolean>(false)
    val submitButtonEnabled: StateFlow<Boolean> = _submitButtonEnabled

    private val _addUserInfoTermsChecked = MutableStateFlow(false)
    val addUserInfoTermsChecked: StateFlow<Boolean> = _addUserInfoTermsChecked

    private val _addUserInfoState = MutableStateFlow<Response<UserInfo>>(Response.Loading)
    val addUserInfoState: StateFlow<Response<UserInfo>> = _addUserInfoState

    fun addUserInfo() {
        addUserInfoUseCase.execute(
            _name.value,
            _lastName.value,
            _birth.value ?: Date(),
            true,
            _addUserInfoTermsChecked.value,
            preferences.user?.email ?: ""
        ).onStart { _addUserInfoState.value = Response.Loading }
            .onEach {
                if(it is Response.EmptySuccess)
                preferences.user?.id?.let { id -> getUserInfo(id) }
            }
            .launchIn(viewModelScope)
    }

    fun onBirthChanged(birth: Date) {
        _birth.value = birth
        addUserInfoButtonActivation()
    }

    private fun getUserInfo(userId: String) {
        getUserInfoUseCase.execute(userId)
            .onStart { _addUserInfoState.value = Response.Loading }
            .onEach { result ->
                if (result is Response.Success) {
                    result.data.let {
                        preferences.user = UserInfo(
                            id = it.id,
                            name = it.name,
                            lastName = it.lastName,
                            authProvider = it.authProvider,
                            email = it.email,
                            imageUrl = it.imageUrl,
                            isRegisterCompleted = it.isRegisterCompleted
                        )
                    }

                    _addUserInfoState.value = result
                } else {
                    Log.d("Nada", "Nada")
                }
            }.launchIn(viewModelScope)
    }

    fun onAddUserInfoTermsChecked(checked: Boolean) {
        _addUserInfoTermsChecked.value = checked
        addUserInfoButtonActivation()
    }

    fun updateProfileBitmap(bitmap: Bitmap) {
        _profileBitmap.value = bitmap
        uploadUserImageUseCase.execute(bitmap)
    }

    fun onNameChanged(name: String) {
        _name.value = name.trim()
        addUserInfoButtonActivation()
    }

    fun onLastNameChanged(lastName: String) {
        _lastName.value = lastName.trim()
        addUserInfoButtonActivation()

    }

    private fun addUserInfoButtonActivation() {
        _submitButtonEnabled.value =
            _name.value.isNotEmpty() && _lastName.value.isNotEmpty() && _birth.value != null && _addUserInfoTermsChecked.value
    }


}