package com.onoffrice.zeldasapi.domain.model

data class ItemDomain(
    val category: String? = "",
    val commonLocations: List<String>? = listOf(),
    val description: String? = "",
    val drops: List<String>? = listOf(),
    val id: Int,
    val image: String? = "",
    val name: String? = ""
)