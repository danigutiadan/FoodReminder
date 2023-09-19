package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.BaseFragment
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui.screens.HomeScreen
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.notifications.FoodNotification
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var alarmManager: AlarmManager

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
                        deleteFoodNotification(it.food.id)
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

    private fun deleteFoodNotification(id: Int?) {
        id?.let {
            try {
                val intent = Intent(context, FoodNotification::class.java)
                alarmManager.cancel(PendingIntent.getBroadcast(context, it, intent, PendingIntent.FLAG_IMMUTABLE))
            } catch (_: Exception) {}

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