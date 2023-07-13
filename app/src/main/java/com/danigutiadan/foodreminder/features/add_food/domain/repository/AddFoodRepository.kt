package com.danigutiadan.foodreminder.features.add_food.domain.repository

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.add_food.domain.models.BarcodeFoodResponse
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AddFoodRepository {

    suspend fun getFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse?>>

     fun saveFood(
         name: String,
         quantity: Int,
         foodType: Int,
         expiryDate: Date,
         daysBeforeExpiration: Int,
         foodBitmap: Bitmap?
     ): Flow<Response<Unit>>
}