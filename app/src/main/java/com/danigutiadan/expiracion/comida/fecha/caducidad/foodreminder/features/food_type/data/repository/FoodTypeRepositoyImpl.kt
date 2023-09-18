package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.data.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.data.datasource.FoodTypeDataSource
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.repository.FoodTypeRepository
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodTypeRepositoryImpl @Inject constructor(private val foodTypeDataSource: FoodTypeDataSource) :
    FoodTypeRepository {

    override fun insertFoodType(foodType: FoodType) =
        foodTypeDataSource.doInsertFoodType(foodType = foodType)

    override fun getAllFoodTypes(): Flow<Response<List<FoodType>>> =
        foodTypeDataSource.doGetAllFoodTypes()

}