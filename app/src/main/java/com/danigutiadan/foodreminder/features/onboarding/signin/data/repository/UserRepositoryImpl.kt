package com.danigutiadan.foodreminder.features.onboarding.signin.data.repository

import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource.UserDataSource
import com.danigutiadan.foodreminder.features.onboarding.signin.domain.repository.UserRepository
import javax.inject.Inject
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource): UserRepository {

    override fun getUserInfo(userId: String): Flow<Response<UserInfo>> = userDataSource.doGetUserInfo(userId)
}