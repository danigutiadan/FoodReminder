package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import androidx.room.Dao
import androidx.room.Query
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodHomeDao {

    @Query("SELECT * FROM food")
    fun getAllFood(): Flow<List<Food>>
}