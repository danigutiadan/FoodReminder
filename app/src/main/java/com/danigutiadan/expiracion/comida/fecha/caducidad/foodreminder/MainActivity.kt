package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.ads.loadInterstitial
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.ads.removeInterstitial
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.components.UpdateAppDialog
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui.AddFoodViewModel
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui.HomeViewModel
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.ui.FoodTypeViewModel
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val foodTypeViewModel: FoodTypeViewModel by viewModels()
    private val addFoodViewModel: AddFoodViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val foodTypes = listOf(
        "category_fruits",
        "category_vegetables",
        "category_meat",
        "category_fish_seafood",
        "category_dairy",
        "category_bread_cereals",
        "category_legumes",
        "category_sweets_desserts",
        "category_beverages",
        "category_frozen_foods",
        "category_canned_goods",
        "category_snacks_appetizers",
        "category_sauces",
        "category_ice_creams",
        "category_juices_soft_drinks",
        "category_pasta_sauces",
        "category_eggs",
        "category_nuts",
        "category_ready_meals",
        "category_breakfast_cereals",
        "category_bakery_products",
        "category_fast_food",
        "category_breakfast_items",
        "category_condiments_spices",
        "category_cheeses",
        "category_soups_broths",
        "category_oils_vinegars",
        "category_ready_dishes",
        "category_gluten_free",
        "category_organic_foods",
        "category_baby_foods"
    )




    @Inject
    lateinit var alarmManager: AlarmManager

    var showUpdateAppDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadInterstitial(this)

        MobileAds.initialize(this)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        checkAppVersion()

        if(preferences.isDatabaseInitialized == false)
            foodTypes.forEach { foodTypeResourceId ->
            foodTypeViewModel.insertFoodType(FoodType(foodTypeNameResource = foodTypeResourceId))
            preferences.isDatabaseInitialized = true
        }

        foodTypeViewModel.getAllFoodTypes()

        setContent {
            SplashScreen()
        }

        if(showUpdateAppDialog.value.not()) {
            homeViewModel.getAllFoodForUpdate()
            lifecycleScope.launch {
                homeViewModel.isAllFoodUpdated.collect { isFoodUpdated ->
                    if (isFoodUpdated) {
                        navigator.navigateToDashboard(this@MainActivity, true)
                    }
                }
            }
        }
//        thread {
//            Thread.sleep(2000)
//
//            runOnUiThread {
//            }
//        }
    }

    override fun onDestroy() {
        removeInterstitial()
        super.onDestroy()
    }
    private fun checkAppVersion() {
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {task ->
            if(task.isSuccessful) {
                val minimumVersion = Firebase.remoteConfig.getDouble("minimum_version_android")
                if(minimumVersion > BuildConfig.VERSION_NAME.toDouble()) {
                    showUpdateAppDialog.value = true

                }
            }
        }
    }

    private fun openPlayStore() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }

    @Preview
    @Composable
    private fun SplashScreen() {
        val isDialogVisible: Boolean by showUpdateAppDialog.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF8BC34A))
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f).padding(bottom = 30.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    )
                }

                Text(text = "By Danigutiadan", style = MaterialTheme.typography.h5, color = Color(
                    0xFF688C34
                ), modifier = Modifier.padding(bottom = 30.dp))
            }

            if(isDialogVisible) {
                UpdateAppDialog {openPlayStore()}
            }
        }
    }
}