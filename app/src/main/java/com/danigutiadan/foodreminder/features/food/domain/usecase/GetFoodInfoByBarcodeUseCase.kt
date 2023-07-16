package com.danigutiadan.foodreminder.features.food.domain.usecase

import com.danigutiadan.foodreminder.features.food.domain.FoodRepository
import javax.inject.Inject

class GetFoodInfoByBarcodeUseCase @Inject constructor(private val foodRepository: FoodRepository) {

    suspend fun execute(barcode: String) = foodRepository.getFoodInfoByBarcode(barcode)
}