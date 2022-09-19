package com.onoffrice.zeldasapi.data.mapper

import com.onoffrice.zeldasapi.data.model.CategoryResponse
import com.onoffrice.zeldasapi.data.model.ItemDataResponse
import com.onoffrice.zeldasapi.domain.model.ItemDomain
import org.junit.Test
import kotlin.test.assertEquals

class ItemToDomainMapperTest {

    private val mapper: ItemToDomainMapper = ItemToDomainMapper()

    @Test
    fun `when mapper should map to domain`() {
        // When
        val result = mapper.map(mockResponse())

        // Then
        assertEquals(result, mockExpected())
    }

    private fun mockResponse() =
        CategoryResponse(
            data = listOf(
                ItemDataResponse(
                    category = "monsters",
                    commonLocations = listOf(),
                    description = "description",
                    drops = listOf(),
                    id = 0,
                    image = "image",
                    name = "name"
                )
            )
        )

    private fun mockExpected() =
        listOf(
            ItemDomain(
                category = "monsters",
                commonLocations = listOf(),
                description = "description",
                drops = listOf(),
                id = 0,
                image = "image",
                name = "name"
            )
        )
}