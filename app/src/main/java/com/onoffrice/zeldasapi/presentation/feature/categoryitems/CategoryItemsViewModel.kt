package com.onoffrice.zeldasapi.presentation.feature.categoryitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onoffrice.zeldasapi.domain.usecase.GetCategoryInfoUseCase
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import com.onoffrice.zeldasapi.utils.Event
import com.onoffrice.zeldasapi.utils.SimpleEvent
import com.onoffrice.zeldasapi.utils.triggerEvent
import com.onoffrice.zeldasapi.utils.triggerPostEvent
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class CategoryItemsViewModel(
    private val getCategoryInfoUseCase: GetCategoryInfoUseCase,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _emptyResponseEvent = MutableLiveData<SimpleEvent>()
    private val _errorResponseEvent = MutableLiveData<SimpleEvent>()
    private val _showLoadingEvent = MutableLiveData<SimpleEvent>()
    private val _successResponseEvent = MutableLiveData<Event<List<ItemPresentation>>>()
    private val _itemSelectedEvent = MutableLiveData<Event<ItemPresentation>>()

    private var currentList = listOf<ItemPresentation>()
    private var filteredList = listOf<ItemPresentation>()

    val emptyResponseEvent: LiveData<SimpleEvent>
        get() = _emptyResponseEvent
    val errorResponseEvent: LiveData<SimpleEvent>
        get() = _errorResponseEvent
    val successResponseEvent: LiveData<Event<List<ItemPresentation>>>
        get() = _successResponseEvent
    val loadingEvent: LiveData<SimpleEvent>
        get() = _showLoadingEvent
    val itemSelectedEvent: LiveData<Event<ItemPresentation>>
        get() = _itemSelectedEvent


    fun getCategoryInfo(category:String) {
        viewModelScope.launch(dispatcher) {
            _showLoadingEvent.triggerEvent()
            runCatching {
                getCategoryInfoUseCase(category)
            }.onSuccess {
                 handlerSuccess(it)
            }.onFailure {
                _errorResponseEvent.triggerEvent()
            }
        }
    }

    private fun handlerSuccess(data: ZeldaPresentation) {
        when (data) {
            is ZeldaPresentation.EmptyResponse -> _emptyResponseEvent.triggerEvent()
            is ZeldaPresentation.ErrorResponse -> _errorResponseEvent.triggerEvent()
            is ZeldaPresentation.CategoryInfoSuccessResponse -> {
                currentList = data.items
                _successResponseEvent.triggerPostEvent(currentList)
            }
        }
    }

    fun onItemSelected(item: ItemPresentation) {
        _itemSelectedEvent.triggerEvent(item)
    }

    fun checkQuerySubmitted(query: String) {
        if (query.isNotEmpty()) {
            filteredList = currentList.filter { it.name.contains(query) }
            _successResponseEvent.triggerPostEvent(filteredList)
        } else {
            _successResponseEvent.triggerPostEvent(currentList)
        }
    }
}