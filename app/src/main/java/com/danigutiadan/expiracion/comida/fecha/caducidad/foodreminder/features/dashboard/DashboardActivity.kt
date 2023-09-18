package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.BaseActivity
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.databinding.ActivityDashboardBinding
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