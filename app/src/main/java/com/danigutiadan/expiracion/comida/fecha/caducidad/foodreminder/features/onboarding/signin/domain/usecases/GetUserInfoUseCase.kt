package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.usecases

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun execute(userId: String): Flow<Response<UserInfo>> =
        userRepository.getUserInfo(userId)

}