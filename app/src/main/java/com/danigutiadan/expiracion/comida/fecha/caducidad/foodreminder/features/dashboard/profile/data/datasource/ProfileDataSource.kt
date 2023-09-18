package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProfileDataSource {

    fun doLogout(): Flow<Response<Void>>
}