package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.api

import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.BarcodeFoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getFoodByBarcode(
        @Query("code") code: String,
        @Query("fields") fields: String = "product_name,image_front_url"
    ): Response<BarcodeFoodResponse>

}