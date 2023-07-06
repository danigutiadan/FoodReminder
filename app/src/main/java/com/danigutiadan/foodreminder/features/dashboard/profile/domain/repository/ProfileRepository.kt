package com.danigutiadan.foodreminder.features.dashboard.profile.domain.repository

import com.danigutiadan.foodreminder.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun logout(): Flow<Result<Void>>
}