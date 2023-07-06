package com.danigutiadan.foodreminder.features.onboarding.data.datasource

import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.foodreminder.firestore.Collections
import com.danigutiadan.foodreminder.firestore.UserFields
import com.danigutiadan.foodreminder.firestore.newFirebaseUser
import com.danigutiadan.foodreminder.utils.Result
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val preferences: Preferences
) : AuthDataSource {
    override fun doGoogleLogin() {
        TODO("Not yet implemented")
    }

    override fun doEmailLogin(email: String, password: String) = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                preferences.user = UserInfo(
                    id = it.user?.uid,
                    name = it.user?.displayName,
                    lastName = "",
                    email = it.user?.email
                )
                trySend(Result.Success(it.user!!))
                close()
            }
            .addOnFailureListener {
                trySend(Result.Error(it))
                close()
            }

        awaitClose()
    }

    override fun doFacebookLogin() {
        TODO("Not yet implemented")
    }


    override fun doEmailRegister(email: String, password: String): Result<FirebaseUser> {
        var registerState: Result<FirebaseUser> = Result.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                addUserToDatabase(email, it)
                registerState = Result.Success(it.user!!)
            }
            .addOnFailureListener { registerState = Result.Error(it) }

        return registerState
    }

    override fun addUserToDatabase(email: String, userData: AuthResult): Result<Void> {
        var addUserToDatabaseState : Result<Void> = Result.Loading
        db.collection(Collections.USERS).document(userData.user?.uid.toString()).set(
            newFirebaseUser(userData)
        ).addOnSuccessListener {
            addUserToDatabaseState = Result.EmptySuccess
        }.addOnFailureListener {
            addUserToDatabaseState = Result.Error(it)
        }
        return addUserToDatabaseState
    }

    override fun addUserInfo(
        name: String,
        lastName: String,
        birth: Date,
        isRegisterCompleted: Boolean,
        termsChecked: Boolean,
        email: String
    ): Flow<Result<Void>>  = callbackFlow{
        var addUserInfoState : Result<Void> = Result.Loading
        preferences.user?.id?.let { userId ->
            db.collection(Collections.USERS).document(userId).set(
                hashMapOf(
                    UserFields.EMAIL to email,
                    UserFields.NAME to name,
                    UserFields.LASTNAME to lastName,
                    UserFields.BIRTH to birth,
                    UserFields.IMAGE_URL to (preferences.user?.imageUrl ?: ""),
                    UserFields.IS_REGISTER_COMPLETED to isRegisterCompleted,
                    UserFields.TERMS_CHECKED to termsChecked)
            ).addOnSuccessListener {
                trySend(Result.EmptySuccess)
                close()
            }.addOnFailureListener {
                trySend(Result.Error(it))
                close()
            }
        }
        awaitClose()
    }


}