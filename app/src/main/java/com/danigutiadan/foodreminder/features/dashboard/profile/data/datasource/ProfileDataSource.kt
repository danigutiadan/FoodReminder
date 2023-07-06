package com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource

import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProfileDataSource {

    fun doLogout(): Flow<Result<Void>>
}