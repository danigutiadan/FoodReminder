package com.danigutiadan.foodreminder.features.onboarding.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases.EmailLoginUseCase
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases.GetUserInfoUseCase
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.usecases.EmailRegisterUseCase
import com.danigutiadan.foodreminder.utils.Result
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val emailLoginUseCase: EmailLoginUseCase,
    private val emailRegisterUseCase: EmailRegisterUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val preferences: Preferences
) :
    ViewModel() {

    private val _emailSignInState = MutableStateFlow<Result<FirebaseUser>>(Result.Loading)
    val emailSignInState: StateFlow<Result<FirebaseUser>> = _emailSignInState

    private val _emailRegisterState = MutableStateFlow<Result<FirebaseUser>>(Result.Loading)
    val emailRegisterState: StateFlow<Result<FirebaseUser>> = _emailRegisterState

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatPassword

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean> = _buttonEnabled

    private val _termsChecked = MutableStateFlow(false)
    val termsChecked: StateFlow<Boolean> = _termsChecked

    fun signInWithEmail() {
        emailLoginUseCase.execute(_email.value, _password.value)
            .onStart { _emailSignInState.value = Result.Loading }
            .onEach { result ->
                _emailSignInState.value = result
                if (result is Result.Success) {
                    getUserInfo(userId = result.data.uid)
                }
            }
            .launchIn(viewModelScope)
    }

    fun registerWithEmail() {
        emailRegisterUseCase.execute(_email.value, _password.value)
            .onStart { _emailRegisterState.value = Result.Loading }
            .onEach { _emailRegisterState.value = it }
            .launchIn(viewModelScope)
    }

    private fun getUserInfo(userId: String) {
        getUserInfoUseCase.execute(userId)
            .onStart { _emailSignInState.value = Result.Loading }
            .onEach { result ->
                if (result is Result.Success) {
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
                }
            }
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email.trim()
        _password.value = password
        _buttonEnabled.value =
            Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6
    }

    fun onSignUpChanged(
        email: String,
        password: String,
        repeatPassword: String,
        termsChecked: Boolean
    ) {
        _email.value = email.trim()
        _password.value = password
        _repeatPassword.value = repeatPassword
        _buttonEnabled.value =
            Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6
                    && repeatPassword == password && termsChecked
    }

    fun resetValues() {
        _email.value = ""
        _password.value = ""
        _repeatPassword.value = ""
        _buttonEnabled.value = false
        _termsChecked.value = false
    }

    fun onTermsChecked(it: Boolean) {
        _termsChecked.value = it
        _buttonEnabled.value =
            Patterns.EMAIL_ADDRESS.matcher(email.value).matches() && password.value.length >= 6
                    && repeatPassword.value == password.value && it
    }
}