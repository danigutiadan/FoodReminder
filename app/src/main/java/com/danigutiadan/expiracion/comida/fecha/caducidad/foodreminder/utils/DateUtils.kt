package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun formatDateToString(date: Date?): String? {
        return if (date != null) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.format(date)
        } else
            null
    }
}