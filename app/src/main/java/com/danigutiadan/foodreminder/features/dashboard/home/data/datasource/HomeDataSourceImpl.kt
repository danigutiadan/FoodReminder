package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import android.util.Log
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.features.food_detail.data.FoodWithFoodType
import com.danigutiadan.foodreminder.utils.Response
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
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

    override fun getAllFood(): Flow<List<FoodWithFoodType>> = flow {
        val response = db.foodHomeDao().getFoodWithFoodType()
        emit(response)
    }.flowOn(Dispatchers.IO)


}