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
import com.onoffrice.zeldasapi.presentation.feature.itemdetail.ItemDetailViewModel
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ItemDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel: ItemDetailViewModel by lazy {
        ItemDetailViewModel()
    }

    private val mockItemPresentation = ItemPresentation(
        category = "monsters",
        commonLocations = listOf(),
        description = "description",
        drops = listOf(),
        id = 0,
        image = "image",
        name = "name")

    @Test
    fun `handleItemDetail should send show item event`() = runBlockingTest {

        // When
        viewModel.handleItemDetail(mockItemPresentation)

        // Then
        viewModel.showItemDetailEvent.verify()
    }
}