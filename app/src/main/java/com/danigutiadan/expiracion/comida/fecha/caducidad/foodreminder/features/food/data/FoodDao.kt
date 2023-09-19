package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert
    fun insertFood(food: Food): Long

    @Update
    fun updateFood(food: Food)

    @Query("SELECT * FROM food")
    fun getAllFood(): Flow<List<Food>>

    @Transaction
    @Query("SELECT * FROM food INNER JOIN food_type ON food.food_type_Id = food_type.id")
    fun getFoodWithFoodType(): List<FoodInfo>

    @Transaction
    @Query("SELECT * FROM food INNER JOIN food_type ON food.food_type_Id = food_type.id WHERE food.food_id = :foodId")
    fun getFoodWithFoodTypeById(foodId: Int): FoodInfo?

    @Delete
    fun deleteFood(food: Food)


    @Transaction
    fun getFoodListWithFilters(
        foodType: Int? = null,
        foodStatus: Int? = null,
        name: String? = null,
        foodOrder: FoodOrder? = null
    ): List<FoodInfo> {
        val queryBuilder = StringBuilder("SELECT * FROM food INNER JOIN food_type ON food.food_type_Id = food_type.id WHERE 1=1")

        val filterArgs = mutableListOf<Any>()

        if (foodType != null) {
            queryBuilder.append(" AND food.food_type_Id = ?")
            filterArgs.add(foodType)
        }

        if (foodStatus != null) {
            queryBuilder.append(" AND food.food_status_int = ?")
            filterArgs.add(foodStatus)
        }

        if (!name.isNullOrBlank()) {
            queryBuilder.append(" AND food.food_name LIKE ?")
            filterArgs.add("%$name%")
        }

        if(foodOrder != null) {
            when(foodOrder) {
                FoodOrder.FOOD_STATUS_ASC -> {
                    queryBuilder.append(" ORDER BY food.food_expiry_date ASC")
                }
                FoodOrder.FOOD_STATUS_DESC -> {
                    queryBuilder.append(" ORDER BY food.food_expiry_date DESC")
                }
            }
        }

        // Execute the final query
        val query = SimpleSQLiteQuery(queryBuilder.toString(), filterArgs.toTypedArray())

        return getFoodWithFoodTypeWithFilters(query)
    }

    @RawQuery
    fun getFoodWithFoodTypeWithFilters(query: SupportSQLiteQuery): List<FoodInfo>

}