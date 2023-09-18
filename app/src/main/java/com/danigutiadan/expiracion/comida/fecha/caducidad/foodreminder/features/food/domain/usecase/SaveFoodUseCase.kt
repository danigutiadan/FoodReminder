package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
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