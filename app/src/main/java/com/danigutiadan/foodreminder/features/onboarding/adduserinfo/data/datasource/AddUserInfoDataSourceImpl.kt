package com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource

import android.graphics.Bitmap
import android.util.Log
import com.danigutiadan.foodreminder.Preferences
import com.danigutiadan.foodreminder.firestore.Collections
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import javax.inject.Inject

class AddUserInfoDataSourceImpl @Inject constructor(private val db: FirebaseFirestore, private val storage: FirebaseStorage, private val preferences: Preferences): AddUserInfoDataSource {
    override fun doAddUserInfo(name: String, lastName: String, birth: LocalDate) {
            db.collection(Collections.USERS).document()

    }

    override fun doUpluadUserImage(imageBitmap: Bitmap) {
        val storageRef = storage.reference
        val bitmapRef = storageRef.child("profile_picture_${preferences.user?.id}.jpg")

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bitmapData = baos.toByteArray()

        bitmapRef.putBytes(bitmapData)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { url ->
                    preferences.user = preferences.user?.apply { ->
                        imageUrl = url.toString()
                    }
                }

            }
            .addOnFailureListener {
                Log.d("Firebase image upload", "Todo malll")
            }


    }


}