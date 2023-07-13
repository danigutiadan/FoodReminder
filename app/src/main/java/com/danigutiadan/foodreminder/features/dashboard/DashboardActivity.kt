package com.danigutiadan.foodreminder.features.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danigutiadan.foodreminder.BaseActivity
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

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

}