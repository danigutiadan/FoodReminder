package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils

import android.content.Context
import android.os.Build
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object DateUtils {

    fun formatDateToString(date: Date?): String {
        return if (date != null) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.format(date)
        } else
            ""
    }


    // Función para obtener el día de una fecha
    fun Date.getMyDay(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    // Función para obtener el mes de una fecha
    fun Date.getMyMonth(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = this
        // Los meses en Calendar están indexados desde 0 (enero) hasta 11 (diciembre)
        return calendar.get(Calendar.MONTH)
    }

    // Función para obtener el año de una fecha
    fun Date.getMyYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar.get(Calendar.YEAR)
    }

    private fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
    }







}