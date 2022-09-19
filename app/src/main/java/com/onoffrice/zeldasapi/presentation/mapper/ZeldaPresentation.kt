package com.onoffrice.zeldasapi.presentation.mapper

import com.onoffrice.zeldasapi.presentation.model.ItemPresentation

sealed class ZeldaPresentation {
    object ErrorResponse : ZeldaPresentation()
    object EmptyResponse : ZeldaPresentation()
    data class CategoryInfoSuccessResponse(val items: List<ItemPresentation>) : ZeldaPresentation()
    data class CategoriesSuccessResponse(val items: List<String>) : ZeldaPresentation()
}