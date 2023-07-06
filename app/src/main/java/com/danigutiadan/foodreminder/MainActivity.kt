package com.danigutiadan.foodreminder

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danigutiadan.foodreminder.features.dashboard.DashboardActivity
import com.danigutiadan.foodreminder.features.onboarding.ui.OnboardingActivity
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Places.initialize(this, "AIzaSyCVVw3jI_eaOdBNx1bN8YffqdGualTGKYI", Locale("es_ES"))

        setContent {
            SplashScreen()
        }

        thread {
            Thread.sleep(2000)

            runOnUiThread {
                if (isLogged == true) {
                    if (preferences.user?.isRegisterCompleted == false) {
                        startActivity(Intent(this, OnboardingActivity::class.java))
                    } else
                        startActivity(Intent(this, DashboardActivity::class.java))
                } else
                    startActivity(Intent(this, DashboardActivity::class.java))
            }

        }
    }

    @Composable
    private fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF5B1934))
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))

                Text(text = "By danigutiadan", style = MaterialTheme.typography.h5)
            }
        }
    }
}