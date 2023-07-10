package com.danigutiadan.foodreminder.features.food_type.domain.repository

import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface FoodTypeRepository {

    fun insertFoodType(foodType: FoodType): Flow<Response<Unit>>

    fun getAllFoodTypes(): Flow<Response<List<FoodType>>>
}