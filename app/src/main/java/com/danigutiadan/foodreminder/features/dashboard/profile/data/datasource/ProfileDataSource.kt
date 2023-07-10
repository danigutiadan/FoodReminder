package com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource

import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProfileDataSource {

    fun doLogout(): Flow<Response<Void>>
}