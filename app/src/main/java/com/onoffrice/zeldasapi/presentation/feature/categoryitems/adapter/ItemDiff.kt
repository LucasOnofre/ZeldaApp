package com.onoffrice.zeldasapi.presentation.feature.categoryitems.adapter

import androidx.recyclerview.widget.DiffUtil
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation

internal object ItemDiff : DiffUtil.ItemCallback<ItemPresentation>() {
    override fun areItemsTheSame(
        oldItem: ItemPresentation,
        newItem: ItemPresentation
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ItemPresentation,
        newItem: ItemPresentation
    ): Boolean {
        return oldItem == newItem
    }
}