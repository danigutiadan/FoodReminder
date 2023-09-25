package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface FoodRepository {
    suspend fun getFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse?>>

    fun saveFood(
        name: String,
        quantity: Int,
        foodType: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodImageUrl: String
    ): Flow<Response<Long>>

    fun updateFood(
        id: Int?,
        name: String,
        quantity: Int,
        foodType: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodImageUrl: String
    ): Flow<Response<Unit>>


    fun getAllFood(): Flow<List<FoodInfo>>
    fun getFoodWithFilters(
        foodType: Int? = null,
        foodStatus: Int? = null,
        name: String? = null,
        foodOrder: FoodOrder? = null
    ): Flow<List<FoodInfo>>
    fun getFoodById(id: Int): Flow<FoodInfo>
    fun deleteFood(food: Food): Flow<Response<Unit>>

    fun updateAllFoodStatus(foodList: List<FoodInfo>): Flow<Response<Unit>>
}