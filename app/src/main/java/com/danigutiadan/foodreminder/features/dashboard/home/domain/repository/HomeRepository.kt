package com.danigutiadan.foodreminder.features.dashboard.home.domain.repository

import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun logout(): Flow<Result<Void>>
}