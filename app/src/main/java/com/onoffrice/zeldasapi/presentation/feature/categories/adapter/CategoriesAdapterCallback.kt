package com.onoffrice.zeldasapi.presentation.feature.categories.adapter

internal sealed class CategoriesAdapterCallback {
    data class OnCategorySelected(val category: String) : CategoriesAdapterCallback()
}