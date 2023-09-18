package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.firestore

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.data.UserInfo
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentSnapshot

fun newFirebaseUser(userData: AuthResult) = hashMapOf(
    UserFields.USER_ID to userData.user?.uid.toString(),
    UserFields.AUTH_PROVIDER to userData.user?.providerId,
    UserFields.USERNAME to userData.additionalUserInfo?.username,
    UserFields.NAME to userData.user?.displayName,
    UserFields.LASTNAME to "",
    UserFields.IMAGE_URL to userData.user?.photoUrl
)

fun mapUserFromFirestore(document: DocumentSnapshot) = UserInfo().apply {
    id = document.getString(UserFields.USER_ID)
    name = document.getString(UserFields.NAME)
    lastName = document.getString(UserFields.LASTNAME)
    authProvider = document.getString(UserFields.AUTH_PROVIDER)
    imageUrl = document.getString(UserFields.IMAGE_URL)
    isRegisterCompleted = document.getBoolean(UserFields.IS_REGISTER_COMPLETED)
}


