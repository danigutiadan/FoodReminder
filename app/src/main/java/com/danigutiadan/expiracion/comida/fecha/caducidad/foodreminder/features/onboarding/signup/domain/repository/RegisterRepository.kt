package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signup.domain.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser

interface RegisterRepository {


fun emailRegister(email: String, password: String): Response<FirebaseUser>
}