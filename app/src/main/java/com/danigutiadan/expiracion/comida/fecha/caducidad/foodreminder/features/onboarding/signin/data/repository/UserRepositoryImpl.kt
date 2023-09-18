package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.data.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.data.datasource.UserDataSource
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource):
    UserRepository {

    override fun getUserInfo(userId: String): Flow<Response<UserInfo>> = userDataSource.doGetUserInfo(userId)
}