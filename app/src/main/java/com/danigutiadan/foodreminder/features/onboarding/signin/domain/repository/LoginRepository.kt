package com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.danigutiadan.foodreminder.utils.Response
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