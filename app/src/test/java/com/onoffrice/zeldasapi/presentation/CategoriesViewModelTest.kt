package com.onoffrice.zeldasapi.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.onoffrice.zeldasapi.domain.usecase.GetCategoryInfoUseCase
import com.onoffrice.zeldasapi.presentation.feature.categoryitems.CategoryItemsViewModel
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.onoffrice.zeldasapi.utils.MainCoroutineRule
import com.onoffrice.zeldasapi.utils.await
import com.onoffrice.zeldasapi.utils.verify
import com.nhaarman.mockitokotlin2.*
import com.onoffrice.zeldasapi.domain.usecase.GetCategoriesUseCase
import com.onoffrice.zeldasapi.presentation.feature.categories.CategoriesViewModel
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CategoriesViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: GetCategoriesUseCase = mock()
    private val viewModel: CategoriesViewModel by lazy {
        CategoriesViewModel(useCase, Dispatchers.Default)
    }

    private val successResponse = ZeldaPresentation.CategoriesSuccessResponse(listOf("Category 1", "Category 2"))


    @Test
    fun `getCategories should return Zelda Presentation CategoriesSuccessResponse`() = runBlockingTest {
        // Given
        whenever(useCase.invoke()).thenReturn(successResponse)

        // When
        viewModel.getCategories()

        // Then
        viewModel.loadingEvent.verify()
        viewModel.successResponseEvent.verify()
    }
}