package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.Preferences
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.firestore.Collections
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.firestore.UserFields
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.firestore.newFirebaseUser
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
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
                trySend(Response.Success(it.user!!))
                close()
            }
            .addOnFailureListener {
                trySend(Response.Error(it))
                close()
            }

        awaitClose()
    }

    override fun doFacebookLogin() {
        TODO("Not yet implemented")
    }


    override fun doEmailRegister(email: String, password: String): Response<FirebaseUser> {
        var registerState: Response<FirebaseUser> = Response.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                addUserToDatabase(email, it)
                registerState = Response.Success(it.user!!)
            }
            .addOnFailureListener { registerState = Response.Error(it) }

        return registerState
    }

    override fun addUserToDatabase(email: String, userData: AuthResult): Response<Void> {
        var addUserToDatabaseState : Response<Void> = Response.Loading
        db.collection(Collections.USERS).document(userData.user?.uid.toString()).set(
            newFirebaseUser(userData)
        ).addOnSuccessListener {
            addUserToDatabaseState = Response.EmptySuccess
        }.addOnFailureListener {
            addUserToDatabaseState = Response.Error(it)
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
    ): Flow<Response<Void>>  = callbackFlow{
        var addUserInfoState : Response<Void> = Response.Loading
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
                trySend(Response.EmptySuccess)
                close()
            }.addOnFailureListener {
                trySend(Response.Error(it))
                close()
            }
        }
        awaitClose()
    }


}