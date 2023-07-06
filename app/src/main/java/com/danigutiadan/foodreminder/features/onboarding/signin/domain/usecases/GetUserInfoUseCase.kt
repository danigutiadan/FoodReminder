package com.danigutiadan.foodreminder.features.onboarding.signin.domain.usecases

import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun execute(userId: String): Flow<Result<UserInfo>> =
        userRepository.getUserInfo(userId)

}