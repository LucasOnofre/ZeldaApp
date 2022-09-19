package com.onoffrice.zeldasapi.data.datasource

import com.onoffrice.zeldasapi.data.api.ZeldaApiService
import com.onoffrice.zeldasapi.data.model.CategoryResponse
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.onoffrice.zeldasapi.data.model.ItemDataResponse
import com.onoffrice.zeldasapi.domain.model.ItemDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val service: ZeldaApiService = mock()
    private val dataSource: RemoteDataSource = RemoteDataSourceImpl(service)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `list category items should return the list from remote data source`() = runBlockingTest {
        val categorySelected = "monsters"
        // Given
        whenever(service.getCategoryInfo(categorySelected)).thenReturn(mockResponse())

        // When
        val result = dataSource.getCategoryInfo(categorySelected)

        // Then
        assertEquals(result, expectedResponse())
    }

    private fun mockResponse() =
        Response.success(
            CategoryResponse(
                data = listOf(
                    ItemDataResponse(
                        category = "monsters",
                        commonLocations = listOf(),
                        description = "description",
                        drops = listOf(),
                        id = 0,
                        image = "image",
                        name = "name"
                    )
                )
            )
        )

    private fun expectedResponse() =
        listOf(
            ItemDomain(
                category = "monsters",
                commonLocations = listOf(),
                description = "description",
                drops = listOf(),
                id = 0,
                image = "image",
                name = "name"
            )
        )
}