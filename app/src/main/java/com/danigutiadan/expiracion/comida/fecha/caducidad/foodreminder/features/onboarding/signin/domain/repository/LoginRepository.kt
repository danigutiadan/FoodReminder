package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import java.util.*

interface LoginRepository {

    fun googleLogin()
    fun emailLogin(email: String, password: String): Flow<Response<FirebaseUser>>
    fun facebookLogin()

    fun addUserInfo(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Response<Void>>

}