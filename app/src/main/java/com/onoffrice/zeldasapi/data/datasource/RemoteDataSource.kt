package com.onoffrice.zeldasapi.data.datasource

import com.onoffrice.zeldasapi.domain.model.ItemDomain

interface RemoteDataSource {
    suspend fun getCategoryInfo(category: String): List<ItemDomain>?
    suspend fun getCreaturesInfo(): List<ItemDomain>?
    suspend fun getCategories(): List<String>
}