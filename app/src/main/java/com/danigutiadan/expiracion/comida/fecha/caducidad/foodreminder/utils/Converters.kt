package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
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

    @TypeConverter
    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    @TypeConverter
    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        if (bitmap != null) {
            val MAX_IMAGE_SIZE = 480 * 640 // 1MB
            val outputStream = ByteArrayOutputStream()
            var compressionQuality = 20 // Rango de calidad de compresión (0-100)
            var imageSize = 0

            do {
                outputStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)
                imageSize = outputStream.size()

                // Reducir la calidad de la compresión si el tamaño de la imagen aún es demasiado grande
                compressionQuality -= 10
            } while (imageSize > MAX_IMAGE_SIZE && compressionQuality >= 10)

            return outputStream.toByteArray()
        }
        return null
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
