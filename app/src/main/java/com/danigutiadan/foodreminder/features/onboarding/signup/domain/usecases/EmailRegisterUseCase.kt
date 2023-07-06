package com.danigutiadan.foodreminder.features.onboarding.signup.domain.usecases

import com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmailRegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {

    fun execute(email: String, password: String) = flow {
        emit(registerRepository.emailRegister(email, password))
    }

}