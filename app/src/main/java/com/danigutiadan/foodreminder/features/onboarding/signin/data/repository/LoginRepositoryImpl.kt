package com.danigutiadan.foodreminder.features.onboarding.signin.data.repository

import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSource
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    LoginRepository {

    override fun googleLogin() {
        TODO("Not yet implemented")
    }

    override fun emailLogin(email: String, password: String): Flow<Response<FirebaseUser>> =
        authDataSource.doEmailLogin(email, password)

    override fun facebookLogin() {
        TODO("Not yet implemented")
    }

    override fun addUserInfo(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Response<Void>> = authDataSource.addUserInfo(name, lastName, birth, isRegisterCompleted, termsChecked, email)

}