package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.api.ApiService
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.BarcodeFoodResponse
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import javax.inject.Inject

class FoodDataSource @Inject constructor(
    private val service: ApiService,
    private val db: FoodReminderDatabase
) {

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
        try {
            val foodInsertedId = db.foodDao().insertFood(food)
            emit(Response.Success(foodInsertedId))
        } catch (e: java.lang.Exception) {
            emit(Response.Error(e))
        }

    }.flowOn(Dispatchers.IO)

    fun doUpdateFood(
        food: Food
    ) = flow {
        emit(Response.Loading)
        try {
            db.foodDao().updateFood(food)
            emit(Response.EmptySuccess)
        } catch (e: java.lang.Exception) {
            emit(Response.Error(e))
        }

    }.flowOn(Dispatchers.IO)

    fun doUpdateFoodList(foodList: List<Food>
    ): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            db.foodDao().updateFoodList(foodList)
            emit(Response.EmptySuccess)

        } catch (e: java.lang.Exception) {
            emit(Response.Error(e))
        }

    }.flowOn(Dispatchers.IO)

    fun getAllFood(): Flow<List<FoodInfo>> = flow {
        val response = db.foodDao().getFoodWithFoodType()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getAllFoodWithFilters(
        foodType: Int? = null,
        foodStatus: Int? = null,
        name: String? = null,
        foodOrder: FoodOrder? = null
    ): Flow<List<FoodInfo>> = flow {
        val response = db.foodDao().getFoodListWithFilters(foodType, foodStatus, name, foodOrder)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getFoodById(id: Int): Flow<FoodInfo> = flow {
        val response = db.foodDao().getFoodWithFoodTypeById(id)!!
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun deleteFood(food: Food): Flow<Response<Unit>> = flow {
        db.foodDao().deleteFood(food)
        emit(Response.EmptySuccess)
    }.flowOn(Dispatchers.IO)
}