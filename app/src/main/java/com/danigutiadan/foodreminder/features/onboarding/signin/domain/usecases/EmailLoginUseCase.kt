package com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases

import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser
import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmailLoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    fun execute(email: String, password: String): Flow<Result<FirebaseUser>> {
        return loginRepository.emailLogin(email, password)
}}