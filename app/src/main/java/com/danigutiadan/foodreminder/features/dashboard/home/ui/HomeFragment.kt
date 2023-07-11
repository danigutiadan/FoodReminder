package com.danigutiadan.foodreminder.features.dashboard.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.danigutiadan.foodreminder.features.add_food.ui.AddFoodActivity
import com.danigutiadan.foodreminder.features.dashboard.DashboardActivity
import com.danigutiadan.foodreminder.features.dashboard.home.ui.screens.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen(buttonListener = {
                   // findNavController().navigate(HomeFragmentDirections.actionDashboardNavigationHomeToAddFoodFragment())
                    (activity as DashboardActivity).navigateToAddFood()
                })
            }
        }
    }

}