package com.danigutiadan.foodreminder.features.food_detail.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.danigutiadan.foodreminder.features.food_detail.FoodType

@Entity(tableName = "food", foreignKeys = [ForeignKey(entity = FoodType::class, parentColumns = ["id"], childColumns = ["foodTypeId"])])
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "foodTypeId", index = true)
    val foodType: String,

)