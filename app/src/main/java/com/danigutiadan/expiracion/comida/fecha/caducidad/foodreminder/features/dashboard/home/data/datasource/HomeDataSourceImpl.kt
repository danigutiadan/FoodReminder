package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.Preferences
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val preferences: Preferences,
    private val db: FoodReminderDatabase
) : HomeDataSource {

    override fun doLogout(): Flow<Response<Void>> = callbackFlow {
        var logoutState: Response<Void> = Response.Loading
        try {
            auth.signOut()
            preferences.user = null
           trySend(Response.EmptySuccess)
            close()
        } catch (e: Exception) {
            trySend(Response.Error(e))
            close()
        }
        awaitClose()
    }




}