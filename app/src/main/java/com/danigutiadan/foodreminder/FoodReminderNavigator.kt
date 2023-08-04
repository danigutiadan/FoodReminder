package com.danigutiadan.foodreminder

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.danigutiadan.foodreminder.features.add_food.ui.AddFoodActivity
import com.danigutiadan.foodreminder.features.dashboard.DashboardActivity
import com.danigutiadan.foodreminder.features.edit_food.ui.EditFoodActivity
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodInfo
import javax.inject.Inject

class FoodReminderNavigator @Inject constructor(private val context: Context) {

    fun navigateToAddFood(activity: FragmentActivity?, clearStack: Boolean) {
        val newIntent = Intent(activity, AddFoodActivity::class.java)
        navigateTo(activity, newIntent)
    }

    fun navigateToEditFood(activity: FragmentActivity?, food: FoodInfo ,clearStack: Boolean) {
        val newIntent = Intent(activity, EditFoodActivity::class.java)
        //newIntent.putExtra("food", Gson().toJson(food))
        newIntent.putExtra("food", food)
        navigateTo(activity, newIntent)
    }

    fun navigateToDashboard(activity: FragmentActivity?, clearStack: Boolean) {
        val newIntent = Intent(activity, DashboardActivity::class.java)
        navigateTo(activity, newIntent, clearStack)
    }


    private fun navigateTo(activity: FragmentActivity?, intent: Intent, clearStack: Boolean? = false) =
        if (clearStack == false) {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } else {
            activity?.finish()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

}