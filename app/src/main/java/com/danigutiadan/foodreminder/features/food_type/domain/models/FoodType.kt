package com.danigutiadan.foodreminder.features.food_type.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "food_type", indices = [Index(value = ["name"], unique = true)])
data class FoodType(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String)