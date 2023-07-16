package com.danigutiadan.foodreminder.features.dashboard.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.danigutiadan.foodreminder.BaseFragment
import com.danigutiadan.foodreminder.features.dashboard.home.ui.screens.HomeScreen
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val foodList: List<FoodWithFoodType> by homeViewModel.foodListState.collectAsState()
                HomeScreen(
                    addFoodButtonListener = {
                        // findNavController().navigate(HomeFragmentDirections.actionDashboardNavigationHomeToAddFoodFragment())
                        navigator.navigateToAddFood(activity, false)
                    }, foodList,
                    onEditButtonPressed = {
                        navigator.navigateToEditFood(activity, it.food.id ?: 0, false)
                    },
                    onDeleteButtonPressed = {
                        homeViewModel.deleteFoodFromList(it)
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllFood()
    }

}