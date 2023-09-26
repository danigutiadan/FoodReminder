package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.MainActivity
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R

class FoodNotification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Verificar si el contexto es nulo
        if (context == null) {
            return
        }


        // Crear un ID único para la notificación
        val notificationId = 123

        // Crear un NotificationManager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Verificar y crear un canal de notificación (solo es necesario en Android 8.0 y versiones posteriores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                context.getString(R.string.food_expiration_notification_title),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val contentIntent = PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        // Crear el contenido de la notificación
        val builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.ic_alarm) // Icono pequeño de la notificación
            .setContentTitle(context.getString(R.string.food_expiration_notification))
            .setStyle(NotificationCompat.BigTextStyle().bigText(context.getString(R.string.food_expiration_notification_details)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        // Mostrar la notificación
        notificationManager.notify(notificationId, builder.build())
    }
}
