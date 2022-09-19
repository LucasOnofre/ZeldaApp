package com.onoffrice.zeldasapi.presentation.feature.itemdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.zeldasapi.R
import com.onoffrice.zeldasapi.databinding.FragmentItemDetailBinding
import com.onoffrice.zeldasapi.presentation.feature.categoryitems.ITEM_SELECTED
import com.onoffrice.zeldasapi.presentation.feature.itemdetail.adapter.CommonLocationsAdapter
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import com.onoffrice.zeldasapi.utils.PicassoUtils
import com.onoffrice.zeldasapi.utils.subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemDetailFragment : Fragment(R.layout.fragment_item_detail) {

    private var _binding: FragmentItemDetailBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private val viewModel: ItemDetailViewModel by viewModel()

    private val adapter by lazy {
        CommonLocationsAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeEvents()
        configureRecyclerView()
        getCategorySelected()

    }
    private fun configureRecyclerView() {
        viewBinding.commonLocationsRv.adapter = adapter

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewBinding.commonLocationsRv.layoutManager = layoutManager
    }

    private fun subscribeEvents() {
        viewModel.showItemDetailEvent.subscribe(this) {
            viewBinding.itemName.text = this.data.name
            viewBinding.itemCategory.text = this.data.category
            viewBinding.itemDescription.text = this.data.description
            (PicassoUtils().loadImage(this.data.image, viewBinding.itemImage))
            setupCommonLocations(this.data.commonLocations)
        }
    }

    private fun setupCommonLocations(commonLocations: List<String>) {
        if (commonLocations.isNotEmpty()) {
            adapter.update(commonLocations)
        }
        else {
            viewBinding.itemCommonLocationsHeader.isVisible = false
        }
    }

    private fun getCategorySelected() {
        val itemSelected = arguments?.getParcelable<ItemPresentation>(ITEM_SELECTED)

        itemSelected?.let {
           viewModel.handleItemDetail(it)
       }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}