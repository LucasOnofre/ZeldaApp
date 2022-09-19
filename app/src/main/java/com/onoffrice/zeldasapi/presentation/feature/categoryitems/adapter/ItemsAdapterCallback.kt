package com.onoffrice.zeldasapi.presentation.feature.categoryitems.adapter

import com.onoffrice.zeldasapi.presentation.model.ItemPresentation

internal sealed class ItemsAdapterCallback {
    data class OnItemSelectedSelected(val item: ItemPresentation) : ItemsAdapterCallback()
}