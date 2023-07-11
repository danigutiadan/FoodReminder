package com.danigutiadan.foodreminder.features.add_food.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BarcodeFoodResponse(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("page_count")
    var pageCount: Int? = null,
    @SerializedName("page_size")
    var pageSize: Int? = null,
    @SerializedName("products")
    var products: List<BarcodeFood>? = null,
    @SerializedName("skip")
    var skip: Int? = null

) : Serializable


data class BarcodeFood(
    @SerializedName("image_front_url")
    var imageUrl: String? = null,
    @SerializedName("product_name")
    var name: String? = null
)
