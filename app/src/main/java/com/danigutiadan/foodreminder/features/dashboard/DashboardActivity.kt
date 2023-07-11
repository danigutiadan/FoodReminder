package com.danigutiadan.foodreminder.features.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.databinding.ActivityDashboardBinding
import com.danigutiadan.foodreminder.features.add_food.ui.AddFoodActivity
import com.danigutiadan.foodreminder.features.add_food.ui.AddFoodTypeViewModel
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.ui.AddUserInfoViewModel
import com.danigutiadan.foodreminder.features.onboarding.ui.MY_PERMISSIONS_REQUEST_CAMERA
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CODE_IMAGE_PICKER
import com.danigutiadan.foodreminder.utils.ImageUtils
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navController = findNavController(R.id.nav_host_fragment)
        setUpNavigation(binding)
    }


    private fun setUpNavigation(binding: ActivityDashboardBinding) {
        binding.bnvDashboard.setupWithNavController(navController)
    }

    fun navigateToAddFood() {
        // Desde la Activity actual, navegar a la Activity de destino
        val intent = Intent(this, AddFoodActivity::class.java)
        startActivity(intent)

    }




}