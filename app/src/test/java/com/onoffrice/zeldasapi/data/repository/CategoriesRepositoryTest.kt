package com.onoffrice.zeldasapi.data.repository

import com.onoffrice.zeldasapi.data.datasource.RemoteDataSource
import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.onoffrice.zeldasapi.domain.model.ItemDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CategoriesRepositoryTest {

    private val dataSource: RemoteDataSource = mock()

    private val repository: CategoriesRepository by lazy {
        CategoriesRepositoryImpl(dataSource)
    }

    private val mockCategoriesResponse = listOf("Category 1", "Category 2")
    private val mockCategorySelected = "creatures"
    private val mockCategoryInfoSuccessResponse = listOf(ItemDomain(
        category = "monsters",
        commonLocations = listOf(),
        description = "description",
        drops = listOf(),
        id = 123,
        image = "image",
        name = "name"
    ))

    @Test
    fun `When get categories from remote data source should return success`() = runBlockingTest {
        // Given
        whenever(dataSource.getCategories()).thenReturn(mockCategoriesResponse)

        // When
        val result = repository.getCategories()

        // Then
        val data = result as ZeldaPresentation.CategoriesSuccessResponse
        assertEquals(data.items.count(), 2)
    }

    @Test
    fun `When get get category items from remote data source should return success`() = runBlockingTest {
        // Given
        whenever(dataSource.getCategoryInfo(mockCategorySelected)).thenReturn(mockCategoryInfoSuccessResponse)

        // When
        val result = repository.getCategoryInfo(mockCategorySelected)

        // Then
        val data = result as ZeldaPresentation.CategoryInfoSuccessResponse
        assertEquals(data.items[0].id, 123)
    }

    @Test
    fun `When get category items from remote data source should return failure`() = runBlockingTest {
        // Given
        whenever(dataSource.getCategoryInfo(mockCategorySelected)).thenReturn(null)

        // When
        val result = repository.getCategoryInfo(mockCategorySelected)

        // Then
        assertEquals(result, ZeldaPresentation.ErrorResponse)
    }

    @Test
    fun `When get category items from remote data source should return empty list`() =
        runBlockingTest {
            // Given
            whenever(dataSource.getCategoryInfo(mockCategorySelected)).thenReturn(listOf())

            // When
            val result = repository.getCategoryInfo(mockCategorySelected)

            // Then
            assertEquals(result, ZeldaPresentation.EmptyResponse)
        }

}