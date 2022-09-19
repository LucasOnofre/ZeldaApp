package com.onoffrice.zeldasapi.presentation.feature.categoryitems.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.zeldasapi.databinding.CategoryInfoItemBinding
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import com.onoffrice.zeldasapi.utils.PicassoUtils

private typealias ItemAdapterCallbackAlias = (ItemsAdapterCallback) -> Unit

internal class ItemsAdapter(
    private val callback: ItemAdapterCallbackAlias
) : ListAdapter<ItemPresentation, RecyclerView.ViewHolder>(ItemDiff) {

    private var items: List<ItemPresentation> = emptyList()
    private lateinit var viewBinding: CategoryInfoItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        viewBinding = CategoryInfoItemBinding.inflate(inflater)

        return ItemsViewHolder(
            CategoryInfoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemsViewHolder -> {
                holder.update(items[position])
            }
        }
    }

    fun update(itemsUpdated: List<ItemPresentation>) {
        items = emptyList()
        items = itemsUpdated
        submitList(items)
    }

    inner class ItemsViewHolder(
        private val binding: CategoryInfoItemBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun update(item: ItemPresentation) {
            binding.itemName.text = item.name

            PicassoUtils().loadImage(item.image, binding.itemImage)

            binding.root.setOnClickListener {
                callback(ItemsAdapterCallback.OnItemSelectedSelected(item))
            }
        }
    }


    override fun getItemCount(): Int = items.size
}