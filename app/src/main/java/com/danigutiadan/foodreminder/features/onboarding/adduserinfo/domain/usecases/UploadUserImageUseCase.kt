package com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.usecases

import android.graphics.Bitmap
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.domain.repository.AddUserInfoRepository
import javax.inject.Inject

class UploadUserImageUseCase @Inject constructor(private val addUserInfoRepository: AddUserInfoRepository) {

    fun execute(imageBitmap: Bitmap) {
        addUserInfoRepository.uploadUserPhoto(imageBitmap)

    }
}