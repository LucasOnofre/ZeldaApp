package com.onoffrice.zeldasapi.domain

import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository
import com.onoffrice.zeldasapi.domain.usecase.GetCategoryInfoUseCase
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.onoffrice.zeldasapi.domain.usecase.GetCategoriesUseCase
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetCategoriesUseCaseTest {

    private val repository: CategoriesRepository = mock()
    private val useCase: GetCategoriesUseCase = GetCategoriesUseCase(repository)

    private val mockCategoriesSuccess = ZeldaPresentation.CategoriesSuccessResponse(
        listOf(
            "Category 1","Category 2"
        ))

    @Test
    fun `when invoke  should return list`() = runBlockingTest {
        // Given
        whenever(repository.getCategories()).thenReturn(mockCategoriesSuccess)

        // When
        val result = useCase.invoke()

        // Then
        assertEquals(result, mockCategoriesSuccess)
    }
}