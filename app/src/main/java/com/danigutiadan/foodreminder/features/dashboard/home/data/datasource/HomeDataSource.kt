package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    fun doLogout(): Flow<Response<Void>>
    fun getAllFood(): Flow<List<Food>>
}