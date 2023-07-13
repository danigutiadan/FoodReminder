package com.danigutiadan.foodreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
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