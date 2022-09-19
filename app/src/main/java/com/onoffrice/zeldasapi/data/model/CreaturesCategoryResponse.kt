package com.onoffrice.zeldasapi.data.model

import com.google.gson.annotations.SerializedName

data class CreaturesCategoryResponse(
    @SerializedName("data")
    val data: FoodResponse
)

data class FoodResponse(
    @SerializedName("food")
    val food: List<ItemDataResponse>
)