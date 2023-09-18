package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserInfo(userId: String): Flow<Response<UserInfo>>
}