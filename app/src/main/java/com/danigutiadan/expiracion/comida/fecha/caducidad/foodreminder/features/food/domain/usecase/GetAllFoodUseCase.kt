package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(private val foodRepository: FoodRepository) {

    fun execute() = foodRepository.getAllFood()
}