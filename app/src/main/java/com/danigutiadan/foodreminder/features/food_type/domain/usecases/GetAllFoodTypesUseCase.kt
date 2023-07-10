package com.danigutiadan.foodreminder.features.food_type.domain.usecases

import com.danigutiadan.foodreminder.features.food_type.domain.repository.FoodTypeRepository
import javax.inject.Inject

class GetAllFoodTypesUseCase @Inject constructor(private val foodTypeRepository: FoodTypeRepository) {

    fun execute() =
        foodTypeRepository.getAllFoodTypes()

}