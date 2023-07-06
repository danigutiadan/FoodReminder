package com.danigutiadan.foodreminder.features.onboarding.data.datasource

import com.danigutiadan.foodreminder.utils.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AuthDataSource {

    fun doGoogleLogin()
    fun doEmailLogin(email: String, password: String): Flow<Result<FirebaseUser>>
    fun doFacebookLogin()


    fun doEmailRegister(email: String, password: String): Result<FirebaseUser>

    fun addUserToDatabase(email: String, userData: AuthResult): Result<Void>

    fun addUserInfo(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Result<Void>>

}