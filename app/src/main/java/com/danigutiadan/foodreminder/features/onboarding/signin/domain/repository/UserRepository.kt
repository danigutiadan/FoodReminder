package com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository

import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserInfo(userId: String): Flow<Result<UserInfo>>
}