package com.danigutiadan.foodreminder.features.food.domain.usecase

import com.danigutiadan.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.foodreminder.utils.Response
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
        foodImageUrl: String
    ): Flow<Response<Unit>> = foodRepository.saveFood(name, quantity, foodType, expiryDate, daysBeforeExpiration, foodImageUrl)
}