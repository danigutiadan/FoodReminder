package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.domain.FoodRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(private val foodDataSource: FoodDataSource):
    FoodRepository {

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
    ): Flow<Response<Long>> {
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

    override fun updateFood(
        id: Int?,
        name: String,
        quantity: Int,
        foodType: Int,
        expiryDate: Date,
        daysBeforeExpiration: Int,
        foodImageUrl: String
    ): Flow<Response<Unit>> {
        val food = Food(
            id = id,
            name = name,
            quantity = quantity,
            foodType = foodType,
            expiryDate = expiryDate,
            daysBeforeExpirationNotification = daysBeforeExpiration,
            foodImageUrl = foodImageUrl
        )
        return foodDataSource.doUpdateFood(food = food)
    }

    override fun getAllFood(): Flow<List<FoodInfo>> =
        foodDataSource.getAllFood()

    override fun getFoodWithFilters(
        foodType: Int?,
        foodStatus: Int?,
        name: String?,
        foodOrder: FoodOrder?
    ) = foodDataSource.getAllFoodWithFilters(foodType, foodStatus, name, foodOrder)

    override fun getFoodById(id: Int): Flow<FoodInfo> =
        foodDataSource.getFoodById(id)

    override fun deleteFood(food: Food): Flow<Response<Unit>> =
        foodDataSource.deleteFood(food)
}