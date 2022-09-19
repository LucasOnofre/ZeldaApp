package com.onoffrice.zeldasapi.domain.usecase

import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository

private const val CREATURE_DIFFERENT_REQUEST = "Creatures"

class GetCategoryInfoUseCase(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(category : String) = if (category == CREATURE_DIFFERENT_REQUEST) {
        repository.getCreatureInfo()
    } else {
        repository.getCategoryInfo(category.lowercase())
    }
}