package com.danigutiadan.foodreminder.features.add_food.data.datasource

import com.danigutiadan.foodreminder.api.ApiService
import com.danigutiadan.foodreminder.features.add_food.domain.models.BarcodeFoodResponse
import com.danigutiadan.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFoodDataSource @Inject constructor(private val service: ApiService) {

    suspend fun doGetFoodInfoByBarcode(barcode: String): Flow<Response<BarcodeFoodResponse>> = flow {
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
}