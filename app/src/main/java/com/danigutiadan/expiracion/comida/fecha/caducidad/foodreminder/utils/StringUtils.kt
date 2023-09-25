package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType

object StringUtils {

    fun getFoodStatusFilterName(foodStatus: FoodStatus?, context: Context): String {
        return when (foodStatus) {
            FoodStatus.FRESH -> context.getString(R.string.fresh)
            FoodStatus.ABOUT_TO_EXPIRE -> context.getString(R.string.near_expiry)
            FoodStatus.ALMOST_EXPIRED -> context.getString(R.string.almost_expired)
            else -> context.getString(R.string.all)
        }
    }

    fun getFoodOrderFilterName(foodOrder: FoodOrder?, context: Context) = when (foodOrder) {
        FoodOrder.FOOD_STATUS_ASC -> context.getString(R.string.food_state_asc)
        FoodOrder.FOOD_STATUS_DESC -> context.getString(R.string.food_state_desc)
        else -> context.getString(R.string.sort_by)
    }

    @Composable
    fun getFoodTypeName(foodType: FoodType): String {
        if (!foodType.foodTypeNameResource.isNullOrBlank()) {
            val resources = LocalContext.current.resources
            val packageName = LocalContext.current.packageName
            val resourceId =
                resources.getIdentifier(foodType.foodTypeNameResource, "string", packageName)
            return resources.getString(resourceId)
        } else if (!foodType.foodTypeName.isNullOrBlank()) {
            return foodType.foodTypeName.toString()
        }
        return ""
    }
}