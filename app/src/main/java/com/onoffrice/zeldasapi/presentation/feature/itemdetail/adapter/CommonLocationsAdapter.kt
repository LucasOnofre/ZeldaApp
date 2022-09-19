package com.onoffrice.zeldasapi.presentation.feature.itemdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.zeldasapi.databinding.CommonLocationsItemBinding

internal class CommonLocationsAdapter(
) : ListAdapter<String, RecyclerView.ViewHolder>(ItemCommonLocationsDiff) {

    private var items: List<String> = emptyList()
    private lateinit var viewBinding: CommonLocationsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        viewBinding = CommonLocationsItemBinding.inflate(inflater)

        return CommonLocationViewHolder(
            CommonLocationsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommonLocationsAdapter.CommonLocationViewHolder -> {
                holder.update(items[position])
            }
        }
    }

    fun update(itemsUpdated: List<String>) {
        items = emptyList()
        items = itemsUpdated
        submitList(items)
    }

    inner class CommonLocationViewHolder(
        private val binding: CommonLocationsItemBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun update(item: String) {
            binding.commonLocationsItemName.text = item

        }
    }


    override fun getItemCount(): Int = items.size
}