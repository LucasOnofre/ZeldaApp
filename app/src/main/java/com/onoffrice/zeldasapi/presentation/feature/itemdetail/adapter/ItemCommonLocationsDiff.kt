package com.onoffrice.zeldasapi.presentation.feature.itemdetail.adapter

import androidx.recyclerview.widget.DiffUtil

internal object ItemCommonLocationsDiff : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}