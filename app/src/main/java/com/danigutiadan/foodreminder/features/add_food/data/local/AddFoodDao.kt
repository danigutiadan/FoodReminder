package com.danigutiadan.foodreminder.features.add_food.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType

@Dao
interface AddFoodDao {

    @Insert
    fun insertFood(food: Food)

}