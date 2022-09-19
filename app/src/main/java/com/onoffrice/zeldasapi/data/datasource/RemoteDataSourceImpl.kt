package com.onoffrice.zeldasapi.data.datasource

import android.util.Log
import com.onoffrice.zeldasapi.data.api.ZeldaApiService
import com.onoffrice.zeldasapi.data.mapper.ItemCreatureToDomainMapper
import com.onoffrice.zeldasapi.data.mapper.ItemToDomainMapper
import com.onoffrice.zeldasapi.data.model.CategoryResponse
import com.onoffrice.zeldasapi.data.model.CreaturesCategoryResponse
import com.onoffrice.zeldasapi.domain.model.ItemDomain
import com.onoffrice.zeldasapi.utils.Extensions


class RemoteDataSourceImpl(
    private val service: ZeldaApiService
) : RemoteDataSource {

    private val mapper = ItemToDomainMapper()
    private val mapperCreatures = ItemCreatureToDomainMapper()

    override suspend fun getCategoryInfo(category: String): List<ItemDomain>? {
        val response = service.getCategoryInfo(category)

        return if (response.isSuccessful) {
            checkCategoryBody(response.body())
        } else {
            Log.d("Request Error Code:", response.code().toString())
            null
        }
    }

    override suspend fun getCreaturesInfo(): List<ItemDomain>? {
        val response = service.getCreaturesInfo()

        return if (response.isSuccessful) {
            checkCreaturesBody(response.body())
        } else {
            Log.d("Request Error Code:", response.code().toString())
            null
        }
    }

    override suspend fun getCategories(): List<String> {
        return listOf(
            "Creatures",
            "Equipment",
            "Materials",
            "Monsters",
            "Treasure"
        )
    }

    private fun checkCategoryBody(data: CategoryResponse?): List<ItemDomain> {
        return if (Extensions.isNullOrEmpty(data)) {
            listOf()
        } else {
            mapper.map(Extensions.orEmpty(data))
        }
    }

    private fun checkCreaturesBody(data: CreaturesCategoryResponse?): List<ItemDomain> {
        return if (Extensions.isNullOrEmpty(data)) {
            listOf()
        } else {
            mapperCreatures.map(Extensions.orEmpty(data))
        }
    }
}