package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    fun doLogout(): Flow<Result<Void>>
}