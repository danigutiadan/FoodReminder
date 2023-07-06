package com.danigutiadan.foodreminder.features.dashboard.profile.domain.usecases

import com.danigutiadan.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import java.util.*
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun execute() =
        profileRepository.logout()

}