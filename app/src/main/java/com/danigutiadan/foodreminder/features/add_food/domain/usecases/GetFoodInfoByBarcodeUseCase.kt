package com.danigutiadan.foodreminder.features.add_food.domain.usecases

import com.danigutiadan.foodreminder.features.add_food.domain.repository.AddFoodRepository
import javax.inject.Inject

class GetFoodInfoByBarcodeUseCase @Inject constructor(private val addFoodRepository: AddFoodRepository) {

    suspend fun execute(barcode: String) = addFoodRepository.getFoodInfoByBarcode(barcode)
}