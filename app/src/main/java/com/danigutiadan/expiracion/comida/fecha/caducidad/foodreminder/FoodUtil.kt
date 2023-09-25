package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodStatus
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date

fun getFoodStatusByDate(date: Date): FoodStatus {
    val expiryDateCalendar = Calendar.getInstance().apply {
        time = date
    }
    val todayCalendar = Calendar.getInstance()
    todayCalendar.set(
        todayCalendar.get(Calendar.YEAR),
        todayCalendar.get(Calendar.MONTH),
        todayCalendar.get(Calendar.DAY_OF_MONTH),
        expiryDateCalendar.get(Calendar.HOUR_OF_DAY),
        expiryDateCalendar.get(Calendar.MINUTE),
        expiryDateCalendar.get(Calendar.SECOND),
        )

    val expiryDateInstant =
        expiryDateCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val todayDateInstant =
        todayCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    val daysBeforeExpired = ChronoUnit.DAYS.between(todayDateInstant, expiryDateInstant)
   return when {
        daysBeforeExpired >= 10L -> FoodStatus.FRESH
        daysBeforeExpired in 1L..9L -> FoodStatus.ABOUT_TO_EXPIRE
        else -> FoodStatus.ALMOST_EXPIRED
    }
}