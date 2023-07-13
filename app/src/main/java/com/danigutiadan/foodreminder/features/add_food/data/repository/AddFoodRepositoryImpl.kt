package com.danigutiadan.foodreminder.features.add_food.data.repository

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.add_food.data.datasource.AddFoodDataSource
import com.danigutiadan.foodreminder.features.add_food.domain.models.BarcodeFoodResponse
import com.danigutiadan.foodreminder.features.add_food.domain.repository.AddFoodRepository
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.utils.ImageUtils
import com.danigutiadan.foodreminder.utils.ImageUtils.bitmapToByteArray
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class AddFoodRepositoryImpl @Inject constructor(
    private val addFoodDataSource: AddFoodDataSource
) :
    AddFoodRepository {

    override suspend fun getFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse?>> {
        return addFoodDataSource.doGetFoodInfoByBarcode(barcode)
    }

    override  fun saveFood(
        name: String,
        quantity: Int,
        foodType: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodBitmap: Bitmap?
    ): Flow<Response<Unit>> {
        val food = Food(
            name = name,
            quantity = quantity,
            foodType = foodType,
            expiryDate = expiryDate,
            daysBeforeExpirationNotification = daysBeforeExpiration,
            image = bitmapToByteArray(foodBitmap)
        )
        return addFoodDataSource.doSaveFood(food)
    }
}