package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import javax.inject.Inject

class GetFoodByIdUseCase @Inject constructor(private val foodRepository: FoodRepository) {
    fun execute(id: Int) = foodRepository.getFoodById(id)
}