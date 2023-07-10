package com.danigutiadan.foodreminder.features.onboarding.data.datasource

import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AuthDataSource {

    fun doGoogleLogin()
    fun doEmailLogin(email: String, password: String): Flow<Response<FirebaseUser>>
    fun doFacebookLogin()


    fun doEmailRegister(email: String, password: String): Response<FirebaseUser>

    fun addUserToDatabase(email: String, userData: AuthResult): Response<Void>

    fun addUserInfo(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Response<Void>>

}