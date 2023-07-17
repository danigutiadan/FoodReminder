package com.danigutiadan.foodreminder.features.food.data

import com.danigutiadan.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(private val foodDataSource: FoodDataSource): FoodRepository {

    override suspend fun getFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse?>> {
        return foodDataSource.doGetFoodInfoByBarcode(barcode)
    }

    override  fun saveFood(
        name: String,
        quantity: Int,
        foodType: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodImageUrl: String
    ): Flow<Response<Unit>> {
        val food = Food(
            name = name,
            quantity = quantity,
            foodType = foodType,
            expiryDate = expiryDate,
            daysBeforeExpirationNotification = daysBeforeExpiration,
            foodImageUrl = foodImageUrl
        )
        return foodDataSource.doSaveFood(food)
    }

    override fun getAllFood(): Flow<List<FoodInfo>> =
        foodDataSource.getAllFood()

    override fun getFoodById(id: Int): Flow<FoodInfo> =
        foodDataSource.getFoodById(id)

    override fun deleteFood(food: Food): Flow<Response<Unit>> =
        foodDataSource.deleteFood(food)
}