package com.danigutiadan.foodreminder.features.food_type.data.datasource

import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import kotlinx.coroutines.flow.Flow
import com.danigutiadan.foodreminder.utils.Response

interface FoodTypeDataSource {

    fun doInsertFoodType(foodType: FoodType): Flow<Response<Unit>>

    fun doGetAllFoodTypes(): Flow<Response<List<FoodType>>>
}