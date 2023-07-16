package com.danigutiadan.foodreminder.features.food.domain

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import com.danigutiadan.foodreminder.utils.Response
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
        foodBitmap: Bitmap?
    ): Flow<Response<Unit>>


    fun getAllFood(): Flow<List<FoodWithFoodType>>
    fun getFoodById(id: Int): Flow<FoodWithFoodType>
    fun deleteFood(food: Food): Flow<Response<Unit>>
}