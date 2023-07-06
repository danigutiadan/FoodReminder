package com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.usecases

import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.LoginRepository
import com.danigutiadan.foodreminder.utils.Result
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
    ): Flow<Result<Void>> {
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