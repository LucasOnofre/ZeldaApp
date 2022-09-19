package com.onoffrice.zeldasapi.data.repository

import com.onoffrice.zeldasapi.data.datasource.RemoteDataSource
import com.onoffrice.zeldasapi.domain.mapper.CategoryToPresentationMapper
import com.onoffrice.zeldasapi.domain.mapper.ItemToPresentationMapper
import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation

class CategoriesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : CategoriesRepository {

    private val mapperItem = ItemToPresentationMapper()
    private val mapperCategory = CategoryToPresentationMapper()

    override suspend fun getCategories(): ZeldaPresentation {
        return mapperCategory.map(remoteDataSource.getCategories())
    }

    override suspend fun getCategoryInfo(category: String): ZeldaPresentation {
        return mapperItem.map(remoteDataSource.getCategoryInfo(category))
    }

    override suspend fun getCreatureInfo(): ZeldaPresentation {
        return mapperItem.map(remoteDataSource.getCreaturesInfo())

    }
}