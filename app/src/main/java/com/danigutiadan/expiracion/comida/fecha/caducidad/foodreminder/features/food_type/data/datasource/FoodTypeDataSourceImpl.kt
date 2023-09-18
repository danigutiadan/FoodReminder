package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOn

class FoodTypeDataSourceImpl @Inject constructor(private val db: FoodReminderDatabase): FoodTypeDataSource {

    override fun doInsertFoodType(foodType: FoodType): Flow<Response<Unit>> = flow<Response<Unit>> {
        try {
            db.foodTypeDao().insertFoodType(foodType)
            emit(Response.EmptySuccess)
        }catch (e: Exception) {
            emit(Response.Error(e))
        }

    }.flowOn(Dispatchers.IO)

    override fun doGetAllFoodTypes(): Flow<Response<List<FoodType>>> = flow {
       // var response: Response<List<FoodType>> = Response.Loading
        try {
            emit(Response.Success(db.foodTypeDao().getAllFoodTypes()))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}