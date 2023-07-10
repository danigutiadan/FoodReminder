package com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource

import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun doGetUserInfo(userId: String): Flow<Response<UserInfo>>
}