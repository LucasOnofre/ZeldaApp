package com.onoffrice.zeldasapi.data.api

import com.onoffrice.zeldasapi.data.model.CategoryResponse
import com.onoffrice.zeldasapi.data.model.CreaturesCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ZeldaApiService {

    @GET("category/{category}")
    suspend fun getCategoryInfo(
        @Path("category") category: String
    ): Response<CategoryResponse>

    @GET("category/creatures")
    suspend fun getCreaturesInfo(
    ): Response<CreaturesCategoryResponse>
}