package com.onoffrice.zeldasapi.utils

import com.onoffrice.zeldasapi.data.model.CategoryResponse
import com.onoffrice.zeldasapi.data.model.CreaturesCategoryResponse
import com.onoffrice.zeldasapi.data.model.FoodResponse

object Extensions{
    fun isNullOrEmpty(response: CategoryResponse?): Boolean {
        return response == null || response.data.isEmpty()
    }

    fun isNullOrEmpty(response: CreaturesCategoryResponse?): Boolean {
        return response == null || response.data.food.isEmpty()
    }

    fun orEmpty(data: CategoryResponse?): CategoryResponse =
        data ?: CategoryResponse(listOf())

    fun orEmpty(data: CreaturesCategoryResponse?): CreaturesCategoryResponse =
        data ?: CreaturesCategoryResponse(FoodResponse(listOf()))

}