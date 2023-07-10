package com.danigutiadan.foodreminder.features.dashboard.profile.data.datasource

import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val preferences: Preferences
) : ProfileDataSource {

    override fun doLogout(): Flow<Response<Void>> = callbackFlow{
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