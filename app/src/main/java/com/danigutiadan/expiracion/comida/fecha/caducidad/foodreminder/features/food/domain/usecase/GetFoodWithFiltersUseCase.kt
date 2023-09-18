package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase


import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import javax.inject.Inject

class GetFoodWithFiltersUseCase @Inject constructor(private val foodRepository: FoodRepository) {

    fun execute(foodType: FoodType? = null, foodStatus: Int? = null, name: String? = null, foodOrder: FoodOrder? = null) =
        foodRepository.getFoodWithFilters(foodType?.id, foodStatus, name, foodOrder)
}