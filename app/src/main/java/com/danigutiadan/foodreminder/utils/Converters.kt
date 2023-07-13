package com.danigutiadan.foodreminder.utils

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }



//    fun intToFoodStatus(value: Int): FoodStatus {
//        return when (value) {
//            FoodStatus.FRESH.value -> FoodStatus.FRESH
//            FoodStatus.ABOUT_TO_EXPIRE.value -> FoodStatus.ABOUT_TO_EXPIRE
//            FoodStatus.ALMOST_EXPIRED.value -> FoodStatus.ALMOST_EXPIRED
//            else -> throw IllegalArgumentException("Unknown FoodStatus value: $value")
//        }
//    }
//
//    @TypeConverter
//    fun foodStatusToInt(status: FoodStatus): Int {
//        return status.value
//    }
}
