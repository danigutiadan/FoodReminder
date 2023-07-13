package com.danigutiadan.foodreminder

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.danigutiadan.foodreminder.features.add_food.ui.AddFoodActivity
import javax.inject.Inject

class FoodReminderNavigator @Inject constructor(private val context: Context) {

    fun navigateToAddFood(activity: FragmentActivity?, clearStack: Boolean) {
        val newIntent = Intent(activity, AddFoodActivity::class.java)
        navigateTo(activity, newIntent)
    }


    private fun navigateTo(activity: FragmentActivity?, intent: Intent, preserveStack: Boolean? = true) =
        if (preserveStack == true) {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } else {
            activity?.finish()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

}