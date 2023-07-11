package com.danigutiadan.foodreminder.features.add_food.domain.repository

import com.danigutiadan.foodreminder.features.add_food.domain.models.BarcodeFoodResponse
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface AddFoodRepository {

    suspend fun getFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse?>>
}