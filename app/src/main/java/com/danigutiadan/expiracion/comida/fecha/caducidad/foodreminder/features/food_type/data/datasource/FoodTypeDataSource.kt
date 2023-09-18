package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface FoodTypeDataSource {

    fun doInsertFoodType(foodType: FoodType): Flow<Response<Unit>>

    fun doGetAllFoodTypes(): Flow<Response<List<FoodType>>>
}