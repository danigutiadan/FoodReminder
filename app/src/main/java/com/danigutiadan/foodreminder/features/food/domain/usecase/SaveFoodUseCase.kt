package com.danigutiadan.foodreminder.features.food.domain.usecase

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.food.domain.FoodRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SaveFoodUseCase @Inject constructor(private val foodRepository: FoodRepository) {

    fun execute(
        name: String,
        quantity: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodType: Int,
        foodBitmap: Bitmap?
    ): Flow<Any> = foodRepository.saveFood(name, quantity, foodType, expiryDate, daysBeforeExpiration, foodBitmap)
}