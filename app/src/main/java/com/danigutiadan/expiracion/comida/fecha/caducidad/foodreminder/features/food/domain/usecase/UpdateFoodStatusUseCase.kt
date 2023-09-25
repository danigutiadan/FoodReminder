package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.usecase

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFoodStatusUseCase @Inject constructor(private val foodRepository: FoodRepository) {
    fun execute(foodList: List<FoodInfo>): Flow<Response<Unit>> = foodRepository.updateAllFoodStatus(foodList)
}