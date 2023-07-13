package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.features.food_detail.data.FoodWithFoodType
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodHomeDao {

    @Query("SELECT * FROM food")
    fun getAllFood(): Flow<List<Food>>

        @Transaction
        @Query("SELECT * FROM food INNER JOIN food_type ON food.food_type_Id = food_type.id")
        fun getFoodWithFoodType(): List<FoodWithFoodType>

}