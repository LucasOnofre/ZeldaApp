package com.onoffrice.zeldasapi.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    val data: List<ItemDataResponse>
)

data class ItemDataResponse(
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("common_locations")
    val commonLocations: List<String>? = listOf(),
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("drops")
    val drops: List<String>? = listOf(),
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String? = ""
)