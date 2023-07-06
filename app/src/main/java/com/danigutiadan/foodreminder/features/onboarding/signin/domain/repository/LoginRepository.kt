package com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow
import java.util.*

interface LoginRepository {

    fun googleLogin()
    fun emailLogin(email: String, password: String): Flow<Result<FirebaseUser>>
    fun facebookLogin()

    fun addUserInfo(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Result<Void>>

}