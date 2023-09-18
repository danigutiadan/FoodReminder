package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.domain.usecases

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class AddUserInfoUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    fun execute(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Response<Void>> {
        return loginRepository.addUserInfo(
            name,
            lastName,
            birth,
            isRegisterCompleted,
            termsChecked,
            email
        )
    }

}