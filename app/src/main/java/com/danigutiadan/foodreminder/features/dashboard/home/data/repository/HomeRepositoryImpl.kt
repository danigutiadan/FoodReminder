package com.danigutiadan.foodreminder.features.dashboard.home.data.repository

import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.HomeDataSource
import com.danigutiadan.foodreminder.features.dashboard.home.domain.repository.HomeRepository
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDataSource: HomeDataSource) :
    HomeRepository {

    override fun logout() =
        homeDataSource.doLogout()




}