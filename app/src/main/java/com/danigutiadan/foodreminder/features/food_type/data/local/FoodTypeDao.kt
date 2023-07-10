package com.danigutiadan.foodreminder.features.food_type.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType

@Dao
interface FoodTypeDao {

    @Insert
    fun insertFoodType(foodType: FoodType)

    @Query("SELECT * FROM food_type")
    fun getAllFoodTypes(): List<FoodType>
}