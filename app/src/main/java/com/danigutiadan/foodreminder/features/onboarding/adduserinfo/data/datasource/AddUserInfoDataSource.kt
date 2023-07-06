package com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource

import android.graphics.Bitmap
import java.time.LocalDate

interface AddUserInfoDataSource {

    fun doAddUserInfo(name: String, lastName: String, birth: LocalDate)
    fun doUpluadUserImage(imageBitmap: Bitmap)
}