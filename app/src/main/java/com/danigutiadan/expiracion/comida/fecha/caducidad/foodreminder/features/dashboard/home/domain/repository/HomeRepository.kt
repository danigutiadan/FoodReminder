package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.domain.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun logout(): Flow<Response<Void>>
}