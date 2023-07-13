package com.danigutiadan.foodreminder.features.add_food.domain.usecases

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.add_food.domain.repository.AddFoodRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SaveFoodUseCase @Inject constructor(private val addFoodRepository: AddFoodRepository) {

     fun execute(
         name: String,
         quantity: Int,
         expiryDate: Date,
         daysBeforeExpiration: Int,
         foodType: Int,
         foodBitmap: Bitmap?
     ): Flow<Any> = addFoodRepository.saveFood(name, quantity, foodType, expiryDate, daysBeforeExpiration, foodBitmap)
}