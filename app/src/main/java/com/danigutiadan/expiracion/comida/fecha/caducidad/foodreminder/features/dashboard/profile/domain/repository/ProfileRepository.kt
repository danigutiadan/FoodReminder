package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun logout(): Flow<Response<Void>>
}