package com.danigutiadan.foodreminder.features.dashboard.home.domain.usecases

import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    fun execute() = homeRepository.getAllFood()
}