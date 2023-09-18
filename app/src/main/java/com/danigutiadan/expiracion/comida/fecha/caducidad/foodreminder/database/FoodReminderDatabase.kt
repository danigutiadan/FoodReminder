package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.FoodDao
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.data.local.FoodTypeDao
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Converters


@Database(entities = [Food::class, FoodType::class], version = 1)
@TypeConverters(Converters::class)
abstract class FoodReminderDatabase: RoomDatabase() {
    abstract fun foodTypeDao(): FoodTypeDao
    abstract fun foodDao(): FoodDao


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