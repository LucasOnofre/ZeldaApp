package com.onoffrice.zeldasapi.domain.usecase

import com.onoffrice.zeldasapi.domain.repository.CategoriesRepository

class GetCategoriesUseCase(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke() = repository.getCategories()
}