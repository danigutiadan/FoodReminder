package com.danigutiadan.foodreminder.features.food.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert
    fun insertFood(food: Food)

    @Query("SELECT * FROM food")
    fun getAllFood(): Flow<List<Food>>

    @Transaction
    @Query("SELECT * FROM food INNER JOIN food_type ON food.food_type_Id = food_type.id")
    fun getFoodWithFoodType(): List<FoodWithFoodType>

    @Transaction
    @Query("SELECT * FROM food INNER JOIN food_type ON food.food_type_Id = food_type.id WHERE food.food_id = :foodId")
    fun getFoodWithFoodTypeById(foodId: Int): FoodWithFoodType?

    @Delete
    fun deleteFood(food: Food)

}