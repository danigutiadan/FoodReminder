package com.danigutiadan.foodreminder.features.dashboard.home.domain.repository

import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun logout(): Flow<Response<Void>>
    fun getAllFood(): Flow<List<Food>>
}