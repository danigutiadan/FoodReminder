package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.usecases

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun execute() =
        profileRepository.logout()

}