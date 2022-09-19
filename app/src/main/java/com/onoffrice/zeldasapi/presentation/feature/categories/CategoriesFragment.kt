package com.onoffrice.zeldasapi.presentation.feature.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.onoffrice.zeldasapi.R
import com.onoffrice.zeldasapi.databinding.FragmentCategoriesBinding
import com.onoffrice.zeldasapi.presentation.feature.categories.adapter.CategoriesAdapter
import com.onoffrice.zeldasapi.presentation.feature.categories.adapter.CategoriesAdapterCallback
import com.onoffrice.zeldasapi.utils.subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CATEGORY_SELECTED_BUNDLE = "CATEGORY_SELECTED_BUNDLE"

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null

    private val viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private val viewModel: CategoriesViewModel by viewModel()

    private val adapter by lazy {
        CategoriesAdapter(::onCategoriesAdapterCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeEvents()

        configureRecyclerView()
        viewModel.getCategories()
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
        viewModel.categorySelectedEvent.subscribe(this) {
            val bundle = bundleOf(CATEGORY_SELECTED_BUNDLE to this.data)
            view?.findNavController()?.navigate(R.id.actionOpenCategoryInfo, bundle)
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

    private fun onCategoriesAdapterCallback(categoriesAdapterCallback: CategoriesAdapterCallback) =
        categoriesAdapterCallback.run {
            when(this) {
                is CategoriesAdapterCallback.OnCategorySelected -> viewModel.onCategorySelected(this.category)
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