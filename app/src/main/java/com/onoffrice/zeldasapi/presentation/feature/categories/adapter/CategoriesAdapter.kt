package com.onoffrice.zeldasapi.presentation.feature.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.zeldasapi.databinding.CategoryItemBinding

private typealias CategoriesAdapterCallbackAlias = (CategoriesAdapterCallback) -> Unit

internal class CategoriesAdapter(
    private val callback: CategoriesAdapterCallbackAlias
) : ListAdapter<String, RecyclerView.ViewHolder>(CategoryDiff) {

    private var items: List<String> = emptyList()
    private lateinit var viewBinding: CategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        viewBinding = CategoryItemBinding.inflate(inflater)

        return CategoriesViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoriesViewHolder -> {
                holder.update(items[position])
            }
        }
    }

    fun update(itemsUpdated: List<String>) {
        items = emptyList()
        items = itemsUpdated
        submitList(items)
    }

    inner class CategoriesViewHolder(
        private val binding: CategoryItemBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun update(item: String) {
            binding.itemName.text = item

            binding.root.setOnClickListener {
                callback(CategoriesAdapterCallback.OnCategorySelected(item))
            }
        }
    }


    override fun getItemCount(): Int = items.size
}