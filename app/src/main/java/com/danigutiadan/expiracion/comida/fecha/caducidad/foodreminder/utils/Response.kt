package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    object EmptySuccess : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
}
