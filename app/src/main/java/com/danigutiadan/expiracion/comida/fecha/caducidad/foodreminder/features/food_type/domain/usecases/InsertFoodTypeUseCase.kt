package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.usecases

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.repository.FoodTypeRepository
import javax.inject.Inject

class InsertFoodTypeUseCase @Inject constructor(private val foodTypeRepository: FoodTypeRepository) {

    fun execute(foodType: FoodType) =
        foodTypeRepository.insertFoodType(foodType = foodType)

}