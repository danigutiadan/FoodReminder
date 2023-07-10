package com.danigutiadan.foodreminder.features.onboarding.signup.data.repository

import com.danigutiadan.foodreminder.features.onboarding.data.datasource.AuthDataSource
import com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository.RegisterRepository
import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource): RegisterRepository {
    override fun emailRegister(email: String, password: String): Response<FirebaseUser> {
        return authDataSource.doEmailRegister(email, password)
    }
}