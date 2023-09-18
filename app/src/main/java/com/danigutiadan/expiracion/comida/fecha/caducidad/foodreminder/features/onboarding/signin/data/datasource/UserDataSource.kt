package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun doGetUserInfo(userId: String): Flow<Response<UserInfo>>
}