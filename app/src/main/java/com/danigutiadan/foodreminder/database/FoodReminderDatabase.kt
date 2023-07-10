package com.danigutiadan.foodreminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase import com.danigutiadan.foodreminder.features.dashboard.home.data.datasource.FoodHomeDao
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.features.food_type.data.local.FoodTypeDao


@Database(entities = [Food::class, FoodType::class], version = 1)
abstract class FoodReminderDatabase: RoomDatabase() {
    abstract fun foodHomeDao(): FoodHomeDao
    abstract fun foodTypeDao(): FoodTypeDao


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