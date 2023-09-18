package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.usecases

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmailLoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    fun execute(email: String, password: String): Flow<Response<FirebaseUser>> {
        return loginRepository.emailLogin(email, password)
}}