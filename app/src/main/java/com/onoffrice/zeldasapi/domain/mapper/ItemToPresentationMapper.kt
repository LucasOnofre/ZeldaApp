package com.onoffrice.zeldasapi.domain.mapper

import com.onoffrice.zeldasapi.domain.model.ItemDomain
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import com.onoffrice.zeldasapi.utils.Mapper


class ItemToPresentationMapper :
    Mapper<List<ItemDomain>?, ZeldaPresentation> {

    override fun map(source: List<ItemDomain>?): ZeldaPresentation {
        return when {
            source == null -> {
                ZeldaPresentation.ErrorResponse
            }
            source.isEmpty() -> {
                ZeldaPresentation.EmptyResponse
            }
            else -> {
                toPresentation(source)
            }
        }
    }

    private fun toPresentation(source: List<ItemDomain>): ZeldaPresentation {
        return ZeldaPresentation.CategoryInfoSuccessResponse(
            source.map {
                ItemPresentation(
                    id = it.id,
                    name = it.name?.replaceFirstChar { char -> char.uppercase() }.orEmpty(),
                    category = it.category?.replaceFirstChar { char -> char.uppercase() }.orEmpty(),
                    commonLocations = it.commonLocations.orEmpty(),
                    description = it.description.orEmpty(),
                    drops = it.drops.orEmpty(),
                    image = it.image.orEmpty()
                )
            }
        )
    }
}