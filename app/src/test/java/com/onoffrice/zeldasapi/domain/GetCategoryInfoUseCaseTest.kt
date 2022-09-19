package com.onoffrice.zeldasapi.domain

import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository
import com.onoffrice.zeldasapi.domain.usecase.GetCategoryInfoUseCase
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetCategoryInfoUseCaseTest {

    private val repository: CategoriesRepository = mock()
    private val useCase: GetCategoryInfoUseCase = GetCategoryInfoUseCase(repository)

    private val mockCategorySelected = "monsters"
    private val mockCategorySelected2 = "Creatures"

    private val mockItemPresentationList = ZeldaPresentation.CategoryInfoSuccessResponse(
        listOf(
            ItemPresentation(
                category = "monsters",
                commonLocations = listOf(),
                description = "description",
                drops = listOf(),
                id = 0,
                image = "image",
                name = "name")))

    private val mockResponseEmptyResponse = ZeldaPresentation.EmptyResponse


    @Test
    fun `when invoke and category is not creatures should return list`() = runBlockingTest {
        // Given
        whenever(repository.getCategoryInfo(mockCategorySelected)).thenReturn(mockItemPresentationList)

        // When
        val result = useCase.invoke(mockCategorySelected)

        // Then
        assertEquals(result, mockItemPresentationList)
    }

    @Test
    fun `when invoke and category is creatures should return list`() = runBlockingTest {
        // Given
        whenever(repository.getCreatureInfo()).thenReturn(mockItemPresentationList)

        // When
        val result = useCase.invoke(mockCategorySelected2)

        // Then
        assertEquals(result, mockItemPresentationList)
    }

    @Test
    fun `when invoke should return empty list`() = runBlockingTest {
        // Given
        whenever(repository.getCategoryInfo(mockCategorySelected)).thenReturn(mockResponseEmptyResponse)

        // When
        val result = useCase.invoke(mockCategorySelected)

        // Then
        assertEquals(result, ZeldaPresentation.EmptyResponse)
    }

    @Test
    fun `when invoke should return throwable`() = runBlockingTest {
        // Given
        whenever(repository.getCategoryInfo(mockCategorySelected)).thenReturn(ZeldaPresentation.ErrorResponse)

        // When
        val result = useCase.invoke(mockCategorySelected)

        // Then
        assertEquals(result, ZeldaPresentation.ErrorResponse)
    }
}