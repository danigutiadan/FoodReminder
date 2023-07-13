package com.danigutiadan.foodreminder.features.dashboard.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.danigutiadan.foodreminder.features.dashboard.home.ui.HomeViewModel
import com.danigutiadan.foodreminder.features.dashboard.profile.ui.screens.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF38B48D))) {
                    ProfileScreen() { viewModel.logout() }
                }
            }
        }

    }
}