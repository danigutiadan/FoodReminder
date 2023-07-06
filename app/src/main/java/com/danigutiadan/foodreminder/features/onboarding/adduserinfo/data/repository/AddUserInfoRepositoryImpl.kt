package com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.repository

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.data.datasource.AddUserInfoDataSource
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.repository.AddUserInfoRepository
import java.time.LocalDate
import javax.inject.Inject

class AddUserInfoRepositoryImpl @Inject constructor(private val addUserInfoDataSource: AddUserInfoDataSource): AddUserInfoRepository {

    override fun addUserInfo(name: String, lastName: String, birth: LocalDate) {
        TODO("Not yet implemented")
    }

    override fun uploadUserPhoto(imageBitmap: Bitmap) {
       addUserInfoDataSource.doUpluadUserImage(imageBitmap)
    }
}