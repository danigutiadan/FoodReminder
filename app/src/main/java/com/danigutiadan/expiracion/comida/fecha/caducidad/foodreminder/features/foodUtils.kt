package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features

import android.content.Context
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import java.util.Calendar
import java.util.Date

object FoodUtils {


    fun isExpiryDateLaterThanToday(expiryDate: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal2.time = expiryDate
        cal1.time = Date()
        return (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) ||
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) ||
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                        cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH))
    }

    fun isExpiryDateTheSameAsToday(expiryDate: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = Date()

        val cal2 = Calendar.getInstance()
        cal2.time = expiryDate

        val sameDay = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
        val sameMonth = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
        val sameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)

        return sameDay && sameMonth && sameYear
    }

    fun isExpiryDateTheSameAsTomorrow(expiryDate: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = Date()

        val cal2 = Calendar.getInstance()
        cal2.time = expiryDate

        // Obtener la fecha de mañana
        cal1.add(Calendar.DAY_OF_MONTH, 1)

        val sameDay = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
        val sameMonth = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
        val sameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)

        return sameDay && sameMonth && sameYear
    }

    fun List<FoodType>.orderedAlphabetic(context: Context): List<FoodType> {
        // Mapear la lista original a una lista de FoodType con los nombres de los recursos
        val foodTypesWithResourceNames = this.map { foodType ->
            val resourceName = foodType.foodTypeNameResource
            val resourceValue = if (resourceName != null) {
                context.getString(context.resources.getIdentifier(resourceName, "string", context.packageName))
            } else {
                foodType.foodTypeName
            }
            foodType.copy(foodTypeName = resourceValue)
        }

        // Ordenar la lista por foodTypeName (los valores de recursos de cadena)
        return foodTypesWithResourceNames.sortedBy { it.foodTypeName }
    }


}

