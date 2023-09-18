package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.navigation

import androidx.navigation.NavController
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.navigation.OnboardingNavigationKeys.Routes.ADD_USER_INFO
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.navigation.OnboardingNavigationKeys.Routes.SIGN_IN
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.navigation.OnboardingNavigationKeys.Routes.SIGN_UP

object OnboardingNavigationKeys {


    object Routes {
        const val SIGN_IN = "sign_in"
        const val SIGN_UP = "sign_up"
        const val ADD_USER_INFO = "add_user_info"
    }
}

fun navigateToSignUp(navController: NavController) = navController.navigate(SIGN_UP)
fun navigateToSignIn(navController: NavController) = navController.navigate(SIGN_IN)
fun navigateToAddUserInfo(navController: NavController) = navController.navigate(ADD_USER_INFO)
