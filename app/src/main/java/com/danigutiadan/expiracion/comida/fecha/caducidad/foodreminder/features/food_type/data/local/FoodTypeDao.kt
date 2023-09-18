package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType

@Dao
interface FoodTypeDao {

    @Insert
    fun insertFoodType(foodType: FoodType)

    @Query("SELECT * FROM food_type")
    fun getAllFoodTypes(): List<FoodType>
}