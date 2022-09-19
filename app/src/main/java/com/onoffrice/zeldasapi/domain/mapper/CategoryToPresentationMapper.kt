package com.onoffrice.zeldasapi.domain.mapper

import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.onoffrice.zeldasapi.utils.Mapper


class CategoryToPresentationMapper :
    Mapper<List<String>, ZeldaPresentation> {

    override fun map(source: List<String>): ZeldaPresentation {
        return when {
            source.isEmpty() -> {
                ZeldaPresentation.EmptyResponse
            }
            else -> {
                toPresentation(source)
            }
        }
    }

    private fun toPresentation(source: List<String>): ZeldaPresentation {
        return ZeldaPresentation.CategoriesSuccessResponse(
            source.map {
                it
            }
        )
    }
}