package com.danigutiadan.foodreminder.features.onboarding.data
import java.io.Serializable


data class UserInfo(
    var id: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var authProvider: String? = null,
    var email: String? = null,
    var imageUrl: String? = null,
    var isRegisterCompleted: Boolean? = false
) : Serializable
