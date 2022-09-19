package com.onoffrice.zeldasapi.data.mapper

import com.onoffrice.zeldasapi.data.model.CategoryResponse
import com.onoffrice.zeldasapi.data.model.CreaturesCategoryResponse
import com.onoffrice.zeldasapi.domain.model.ItemDomain
import com.onoffrice.zeldasapi.utils.Mapper

class ItemToDomainMapper : Mapper<CategoryResponse, List<ItemDomain> > {
    override fun map(source: CategoryResponse): List<ItemDomain> {
        return source.data.map {
          ItemDomain(
                name = it.name.orEmpty(),
                category = it.category.orEmpty(),
                commonLocations = it.commonLocations.orEmpty(),
                description = it.description.orEmpty(),
                drops = it.drops.orEmpty(),
                id = it.id,
                image = it.image.orEmpty()
            )
        }
    }
}

class ItemCreatureToDomainMapper : Mapper<CreaturesCategoryResponse, List<ItemDomain> > {
    override fun map(source: CreaturesCategoryResponse): List<ItemDomain> {
        return source.data.food.map {
            ItemDomain(
                name = it.name.orEmpty(),
                category = it.category.orEmpty(),
                commonLocations = it.commonLocations.orEmpty(),
                description = it.description.orEmpty(),
                drops = it.drops.orEmpty(),
                id = it.id,
                image = it.image.orEmpty()
            )
        }
    }
}