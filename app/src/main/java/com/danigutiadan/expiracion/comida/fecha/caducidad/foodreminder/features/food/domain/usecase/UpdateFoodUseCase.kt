package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class UpdateFoodUseCase @Inject constructor(private val foodRepository: FoodRepository) {

    fun execute(
        name: String,
        quantity: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodType: Int,
        foodImageUrl: String,
        id: Int? = 0
    ): Flow<Any> = foodRepository.updateFood(id, name, quantity, foodType, expiryDate, daysBeforeExpiration, foodImageUrl)
}