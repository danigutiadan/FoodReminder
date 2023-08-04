package com.danigutiadan.foodreminder.features.dashboard.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.danigutiadan.foodreminder.BaseFragment
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.features.dashboard.home.ui.screens.HomeScreen
import com.danigutiadan.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setCollectors()
        return ComposeView(requireContext()).apply {
            setContent {
                val foodList: List<FoodInfo> by homeViewModel.foodListState.collectAsState()
                val searchFoodInput: String by homeViewModel.searchFoodInputState.collectAsState()
                val foodStatusSelectedOption: FoodStatus? by homeViewModel.foodStatusFilterState.collectAsState()
                val foodTypeSelectedOption: FoodType? by homeViewModel.foodTypeFilterState.collectAsState()
                val foodTypeList: List<FoodType> by homeViewModel.foodTypeListState.collectAsState()
                val foodFiltersTextState: String by homeViewModel.foodFiltersTextState.collectAsState()
                val foodOrderList = listOf(FoodOrder.FOOD_STATUS_ASC, FoodOrder.FOOD_STATUS_DESC)
                HomeScreen(
                    searchFoodInput = searchFoodInput,
                    addFoodButtonListener = {
                        // findNavController().navigate(HomeFragmentDirections.actionDashboardNavigationHomeToAddFoodFragment())
                        navigator.navigateToAddFood(activity, false)
                    },
                    foodList,
                    onEditButtonPressed = {
                        navigator.navigateToEditFood(activity, it, false)
                    },
                    onDeleteButtonPressed = {
                        homeViewModel.deleteFoodFromList(it)
                    },
                    onSearchFoodChanged = {
                        homeViewModel.onSearchFoodChanged(it)
                    },
                    foodStatusSelectedOption = foodStatusSelectedOption,
                    foodTypeSelectedOption = foodTypeSelectedOption,
                    onFoodStatusFilterSelectedOption = { homeViewModel.updateFoodStatusFilter(it) },
                    onFoodTypeFilterSelectedOption = { homeViewModel.updateFoodTypeFilter(it) },
                    onApplyFilters = { homeViewModel.getFoodWithFilters() },
                    foodTypeList = foodTypeList,
                    foodFiltersTextState = manageFoodFiltersText(homeViewModel.foodStatusFilterState.collectAsState(), homeViewModel.foodTypeFilterState.collectAsState()),
                    foodOrderList = foodOrderList,
                    onFoodOrderSelected = {
                        homeViewModel.updateFoodOrder(it)
                    },
                    foodOrderText = StringUtils.getFoodOrderFilterName(
                        homeViewModel.foodOrderState.collectAsState().value,
                        context
                    )
                )
            }
        }
    }

    private fun manageFoodFiltersText(
        foodStatusFilterState: State<FoodStatus?>,
        foodTypeFilterState: State<FoodType?>
    ): String {
        var filtersCount: Int = if (foodStatusFilterState.value != null) {
            1
        } else {
            0
        }
        if (foodTypeFilterState.value != null) {
            filtersCount += 1
        }

        return when (filtersCount) {
            1 -> {
                "${context?.getString(R.string.filters)}: 1"
            }

            2 -> {
                "${context?.getString(R.string.filters)}: 2"
            }

            else -> {
                context?.getString(R.string.filter) ?: ""
            }

        }
    }

    private fun setCollectors() {
        homeViewModel.getFoodTypeList()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getFoodWithFilters()
    }

}