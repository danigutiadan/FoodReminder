package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.data.datasource

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    fun doLogout(): Flow<Response<Void>>
}