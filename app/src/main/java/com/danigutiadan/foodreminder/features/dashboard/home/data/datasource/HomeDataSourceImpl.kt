package com.danigutiadan.foodreminder.features.dashboard.home.data.datasource

import android.util.Log
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.database.FoodReminderDatabase
import com.danigutiadan.foodreminder.features.food_detail.data.Food
import com.danigutiadan.foodreminder.utils.Result
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val preferences: Preferences,
    private val placesClient: PlacesClient,
    private val db: FoodReminderDatabase
) : HomeDataSource {

    override fun doLogout(): Flow<Result<Void>> = callbackFlow {
        var logoutState: Result<Void> = Result.Loading
        try {
            auth.signOut()
            preferences.user = null
           trySend(Result.EmptySuccess)
            close()
        } catch (e: Exception) {
            trySend(Result.Error(e))
            close()
        }
        awaitClose()
    }

    override fun getAllFood(): Flow<List<Food>> {
        return db.foodHomeDao().getAllFood()
    }

    fun doSearchPlaces(input: String = "Restaurante tenerife") {
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setQuery(input)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                Log.d("respuestaaa", response.toString())
            }


    }


}