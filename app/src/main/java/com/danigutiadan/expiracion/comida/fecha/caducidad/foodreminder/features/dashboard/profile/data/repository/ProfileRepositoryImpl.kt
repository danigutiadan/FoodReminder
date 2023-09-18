package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.data.repository

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.data.datasource.HomeDataSource
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val homeDataSource: HomeDataSource) :
    ProfileRepository {

    override fun logout() =
        homeDataSource.doLogout()


}