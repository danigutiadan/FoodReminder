package com.danigutiadan.foodreminder.features.onboarding.signup.domain.repository

import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser

interface RegisterRepository {


fun emailRegister(email: String, password: String): Response<FirebaseUser>
}