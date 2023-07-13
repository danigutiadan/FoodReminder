package com.danigutiadan.foodreminder.database

import com.danigutiadan.foodreminder.utils.Converters
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danigutiadan.foodreminder.features.add_food.data.local.AddFoodDao
import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.FoodHomeDao
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.features.food_type.data.local.FoodTypeDao


@Database(entities = [Food::class, FoodType::class], version = 1)
@TypeConverters(Converters::class)
abstract class FoodReminderDatabase: RoomDatabase() {
    abstract fun foodHomeDao(): FoodHomeDao
    abstract fun foodTypeDao(): FoodTypeDao
    abstract fun addFoodDao(): AddFoodDao


    companion object {
        @Volatile
        private var instance: FoodReminderDatabase? = null

        fun getInstance(context: Context): FoodReminderDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FoodReminderDatabase::class.java,
                    "food_reminder_db"
                ).build()
                    .also { instance = it }
            }

        }
    }

}