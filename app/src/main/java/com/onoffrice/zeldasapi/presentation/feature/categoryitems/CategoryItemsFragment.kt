package com.onoffrice.zeldasapi.presentation.feature.categoryitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.zeldasapi.R
import com.onoffrice.zeldasapi.presentation.feature.categoryitems.adapter.ItemsAdapter
import com.onoffrice.zeldasapi.utils.subscribe
import com.google.android.material.snackbar.Snackbar
import com.onoffrice.zeldasapi.databinding.FragmentCategoryItemsBinding
import com.onoffrice.zeldasapi.presentation.feature.categories.CATEGORY_SELECTED_BUNDLE
import com.onoffrice.zeldasapi.presentation.feature.categoryitems.adapter.ItemsAdapterCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ITEM_SELECTED = "ITEM_SELECTED"

class CategoryItemsFragment : Fragment(R.layout.fragment_category_items) {

    private var _binding: FragmentCategoryItemsBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryItemsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private val viewModel: CategoryItemsViewModel by viewModel()

    private val adapter by lazy {
        ItemsAdapter(::onItemsAdapterCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeEvents()
        configureRecyclerView()
        getCategorySelected()
        handleSearchBar()
    }

    private fun handleSearchBar() {
        viewBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               query?.let {
                   viewModel.checkQuerySubmitted(query)
               }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    viewModel.checkQuerySubmitted(query)
                }
                return false
            }
        })
    }

    private fun getCategorySelected() {
        val categorySelected = arguments?.getString(CATEGORY_SELECTED_BUNDLE)

       categorySelected?.let {
           viewModel.getCategoryInfo(it)
       }
    }

    private fun subscribeEvents() {
        viewModel.loadingEvent.subscribe(this) {
            viewBinding.loadingBar.isVisible = true
        }


        viewModel.emptyResponseEvent.subscribe(this) {
            showFullResultsSnackbar()
            hideLoading()
        }


        viewModel.errorResponseEvent.subscribe(this) {
            showGenericErrorSnackbar()
            hideLoading()
        }

        viewModel.successResponseEvent.subscribe(this) {
            adapter.update(this.data)
            hideLoading()
        }

        viewModel.itemSelectedEvent.subscribe(this) {
            val bundle = bundleOf(ITEM_SELECTED to this.data)
            view?.findNavController()?.navigate(R.id.actionOpenItemDetail, bundle)
        }
    }

    private fun hideLoading() {
        viewBinding.loadingBar.isVisible = false
    }

    private fun configureRecyclerView() {
        viewBinding.recyclerViewItems.adapter = adapter

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewBinding.recyclerViewItems.layoutManager = layoutManager
    }

    private fun onItemsAdapterCallback(itemsAdapterCallback: ItemsAdapterCallback) =
        itemsAdapterCallback.run {
            when(this) {
                is ItemsAdapterCallback.OnItemSelectedSelected -> viewModel.onItemSelected(this.item)
            }
        }

    private fun showFullResultsSnackbar() {
        var snackbar: Snackbar? = null
        snackbar = Snackbar.make(
            viewBinding.recyclerViewItems,
            R.string.full_results,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.back_to_top) {
                viewBinding.recyclerViewItems.scrollToPosition(0)
                snackbar?.dismiss()
            }
        snackbar.show()
    }

    private fun showGenericErrorSnackbar() {
        var snackbar: Snackbar? = null
        snackbar = Snackbar.make(
            viewBinding.recyclerViewItems,
            R.string.couldnt_load,
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}