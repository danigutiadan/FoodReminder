package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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