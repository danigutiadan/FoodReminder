package com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.repository

import android.graphics.Bitmap
import java.time.LocalDate

interface AddUserInfoRepository {

    fun addUserInfo(name: String, lastName: String, birth: LocalDate)
    fun uploadUserPhoto(imageBitmap: Bitmap)
}