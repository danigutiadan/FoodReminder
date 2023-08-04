package com.danigutiadan.foodreminder.features.food.domain.usecase

import com.danigutiadan.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import javax.inject.Inject

class GetFoodWithFiltersUseCase @Inject constructor(private val foodRepository: FoodRepository) {

    fun execute(foodType: FoodType? = null, foodStatus: Int? = null, name: String? = null, foodOrder: FoodOrder? = null) =
        foodRepository.getFoodWithFilters(foodType?.id, foodStatus, name, foodOrder)
}