package com.danigutiadan.foodreminder.features.food.domain.usecase

import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.domain.FoodRepository
import javax.inject.Inject

class DeleteFoodUseCase @Inject constructor(private val foodRepository: FoodRepository) {
    fun execute(food: Food) = foodRepository.deleteFood(food)
}