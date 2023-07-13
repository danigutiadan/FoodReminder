package com.danigutiadan.foodreminder.features.add_food.data.datasource

import com.danigutiadan.foodreminder.api.ApiService
import com.danigutiadan.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.foodreminder.features.add_food.domain.models.BarcodeFoodResponse
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import javax.inject.Inject

class AddFoodDataSource @Inject constructor(private val service: ApiService, private val db: FoodReminderDatabase) {

    suspend fun doGetFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse>> =
        flow {
            emit(Response.Loading)
            try {
                val response = service.getFoodByBarcode(barcode)
                if (response.isSuccessful) {
                    val foodInfoState = response.body()
                    if (foodInfoState != null) {
                        emit(Response.Success(foodInfoState))
                    } else {
                        emit(Response.Error(java.lang.Exception("Null response body")))
                    }
                } else {
                    emit(Response.Error(java.lang.Exception(response.message())))
                }
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }

     fun doSaveFood(
        food: Food
    ) = flow {
        emit(Response.Loading)
        db.addFoodDao().insertFood(food)

    }.flowOn(Dispatchers.IO)
}