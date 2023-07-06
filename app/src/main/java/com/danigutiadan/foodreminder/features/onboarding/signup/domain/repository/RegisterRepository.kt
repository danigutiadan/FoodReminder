package com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository

import com.danigutiadan.foodreminder.utils.Result
import com.google.firebase.auth.FirebaseUser

interface RegisterRepository {


fun emailRegister(email: String, password: String): Result<FirebaseUser>
}