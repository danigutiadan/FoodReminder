package com.danigutiadan.foodreminder.features.dashboard.home.data.repository

import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSource
import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.features.food_detail.data.FoodWithFoodType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDataSource: HomeDataSource) :
    HomeRepository {

    override fun logout() =
        homeDataSource.doLogout()

    override fun getAllFood(): Flow<List<FoodWithFoodType>> =
        homeDataSource.getAllFood()


}