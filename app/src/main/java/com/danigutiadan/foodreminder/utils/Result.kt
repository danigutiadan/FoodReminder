package com.danigutiadan.foodreminder.utils

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object EmptySuccess : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
