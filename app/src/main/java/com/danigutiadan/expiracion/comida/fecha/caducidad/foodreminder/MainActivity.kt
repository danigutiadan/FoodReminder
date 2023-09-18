package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.ui.FoodTypeViewModel
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.ui.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val foodTypeViewModel: FoodTypeViewModel by viewModels()
    private val foodTypes = listOf(
        R.string.category_fruits,
        R.string.category_vegetables,
        R.string.category_meat,
        R.string.category_fish_seafood,
        R.string.category_dairy,
        R.string.category_bread_cereals,
        R.string.category_legumes,
        R.string.category_sweets_desserts,
        R.string.category_beverages,
        R.string.category_frozen_foods,
        R.string.category_canned_goods,
        R.string.category_snacks_appetizers,
        R.string.category_sauces,
        R.string.category_ice_creams,
        R.string.category_juices_soft_drinks,
        R.string.category_pasta_sauces,
        R.string.category_eggs,
        R.string.category_nuts,
        R.string.category_ready_meals,
        R.string.category_breakfast_cereals,
        R.string.category_bakery_products,
        R.string.category_fast_food,
        R.string.category_breakfast_items,
        R.string.category_condiments_spices,
        R.string.category_cheeses,
        R.string.category_soups_broths,
        R.string.category_oils_vinegars,
        R.string.category_ready_dishes,
        R.string.category_gluten_free,
        R.string.category_organic_foods,
        R.string.category_baby_foods,
    )

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(preferences.isDatabaseInitialized == false)
        foodTypes.forEach { foodType ->
            foodTypeViewModel.insertFoodType(FoodType(name = getString(foodType)))
            preferences.isDatabaseInitialized = true
        }

        foodTypeViewModel.getAllFoodTypes()

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
                        navigator.navigateToDashboard(this, true)
                } else
                    navigator.navigateToDashboard(this, true)
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