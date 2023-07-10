package com.danigutiadan.foodreminder.features.onboarding.signin.data.datasource

import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.firestore.Collections
import com.danigutiadan.foodreminder.firestore.mapUserFromFirestore
import com.danigutiadan.foodreminder.utils.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val db: FirebaseFirestore): UserDataSource {

    override fun doGetUserInfo(userId: String): Flow<Response<UserInfo>> = callbackFlow {
        db.collection(Collections.USERS).document(userId).get()
            .addOnSuccessListener {
                val user = mapUserFromFirestore(it)
                trySend(Response.Success(user))
                close()
            }
            .addOnFailureListener {
                trySend(Response.Error(it))
                close()
            }
        awaitClose()
    }
}