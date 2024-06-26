package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "food_type", indices = [Index(value = ["food_type_name"], unique = true)])
data class FoodType(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "food_type_name_resource")
    var foodTypeNameResource: String? = null,
    @ColumnInfo(name = "food_type_name")
    var foodTypeName: String? = null): Serializable