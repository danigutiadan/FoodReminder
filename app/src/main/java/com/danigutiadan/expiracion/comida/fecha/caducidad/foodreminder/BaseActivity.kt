package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    var isLogged: Boolean? = null

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var navigator: FoodReminderNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLogged = preferences.user != null
    }

}