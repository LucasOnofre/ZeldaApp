package com.onoffrice.zeldasapi.domain.repository

import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation

interface CategoriesRepository {
    suspend fun getCategoryInfo(category: String): ZeldaPresentation
    suspend fun getCreatureInfo(): ZeldaPresentation
    suspend fun getCategories(): ZeldaPresentation
}