package com.danigutiadan.foodreminder.features.add_food.data.repository

import com.danigutiadan.foodreminder.features.add_food.data.datasource.AddFoodDataSource
import com.danigutiadan.foodreminder.features.add_food.domain.models.BarcodeFoodResponse
import com.danigutiadan.foodreminder.features.add_food.domain.repository.AddFoodRepository
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFoodRepositoryImpl @Inject constructor(
    private val addFoodDataSource: AddFoodDataSource
) :
    AddFoodRepository {

    override suspend fun getFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse?>> {
        return addFoodDataSource.doGetFoodInfoByBarcode(barcode)
    }
}