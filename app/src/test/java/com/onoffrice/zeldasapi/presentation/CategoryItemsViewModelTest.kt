package com.onoffrice.zeldasapi.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.onoffrice.zeldasapi.domain.usecase.GetCategoryInfoUseCase
import com.onoffrice.zeldasapi.presentation.feature.categoryitems.CategoryItemsViewModel
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.onoffrice.zeldasapi.utils.MainCoroutineRule
import com.onoffrice.zeldasapi.utils.await
import com.onoffrice.zeldasapi.utils.verify
import com.nhaarman.mockitokotlin2.*
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CategoryItemsViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: GetCategoryInfoUseCase = mock()
    private val viewModel: CategoryItemsViewModel by lazy {
        CategoryItemsViewModel(useCase, Dispatchers.Default)
    }

    private val mockCategorySelected = "monsters"
    private val mockItemPresentation = ItemPresentation(
        category = "monsters",
        commonLocations = listOf(),
        description = "description",
        drops = listOf(),
        id = 0,
        image = "image",
        name = "name")

    private val successResponse = ZeldaPresentation.CategoryInfoSuccessResponse(listOf(
        mockItemPresentation
    ))

    private val emptyResponse = ZeldaPresentation.EmptyResponse

    private val errorResponse = ZeldaPresentation.ErrorResponse

    @Test
    fun `get categoryInfo should return Zelda Presentation CategoryInfoSuccessResponse`() = runBlockingTest {
        // Given
        whenever(useCase.invoke(any())).thenReturn(successResponse)

        // When
        viewModel.getCategoryInfo(mockCategorySelected)

        // Then
        verify(useCase).invoke(mockCategorySelected)
        viewModel.loadingEvent.verify()
        viewModel.successResponseEvent.verify {
            assertEquals(0, it!!.data[0].id)
            assertEquals("monsters", it.data[0].category)
        }
    }

    @Test
    fun `get categoryInfo should return Zelda Presentation EmptyResponse`() = runBlockingTest {
        // Given
        whenever(useCase.invoke(any())).thenReturn(emptyResponse)

        // When
        viewModel.getCategoryInfo(mockCategorySelected)

        // Then
        verify(useCase).invoke(mockCategorySelected)
        viewModel.loadingEvent.verify()
        viewModel.emptyResponseEvent.verify()
    }

    @Test
    fun `get categoryInfo should return Zelda Presentation ErrorResponse`() = runBlockingTest {
        // Given
        whenever(useCase.invoke(any())).thenReturn(errorResponse)

        // When
        viewModel.getCategoryInfo(mockCategorySelected)

        // Then
        verify(useCase).invoke(mockCategorySelected)
        viewModel.loadingEvent.verify()
        viewModel.errorResponseEvent.verify()
    }


    @Test
    fun `when onItemSelected should send item selected event`() = runBlockingTest {
        // When
        viewModel.onItemSelected(mockItemPresentation)
        viewModel.itemSelectedEvent.verify()
    }

    @Test
    fun `when checkQuerySubmitted should send event with filtered list`() = runBlockingTest {
        // When
        viewModel.checkQuerySubmitted("test")

        // Then
        viewModel.successResponseEvent.verify()
    }
}