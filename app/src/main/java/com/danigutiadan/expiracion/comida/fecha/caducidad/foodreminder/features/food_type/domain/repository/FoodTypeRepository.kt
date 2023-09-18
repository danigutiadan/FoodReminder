package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface FoodTypeRepository {

    fun insertFoodType(foodType: FoodType): Flow<Response<Unit>>

    fun getAllFoodTypes(): Flow<Response<List<FoodType>>>
}