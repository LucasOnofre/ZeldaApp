package com.onoffrice.zeldasapi.presentation.feature.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onoffrice.zeldasapi.domain.usecase.GetCategoriesUseCase
import com.onoffrice.zeldasapi.presentation.mapper.ZeldaPresentation
import com.onoffrice.zeldasapi.utils.Event
import com.onoffrice.zeldasapi.utils.SimpleEvent
import com.onoffrice.zeldasapi.utils.triggerEvent
import com.onoffrice.zeldasapi.utils.triggerPostEvent
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class CategoriesViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _emptyResponseEvent = MutableLiveData<SimpleEvent>()
    private val _errorResponseEvent = MutableLiveData<SimpleEvent>()
    private val _showLoadingEvent = MutableLiveData<SimpleEvent>()
    private val _successResponseEvent = MutableLiveData<Event<List<String>>>()
    private val _categorySelectedEvent = MutableLiveData<Event<String>>()

    val emptyResponseEvent: LiveData<SimpleEvent>
        get() = _emptyResponseEvent
    val errorResponseEvent: LiveData<SimpleEvent>
        get() = _errorResponseEvent
    val successResponseEvent: LiveData<Event<List<String>>>
        get() = _successResponseEvent
    val loadingEvent: LiveData<SimpleEvent>
        get() = _showLoadingEvent
    val categorySelectedEvent: LiveData<Event<String>>
        get() = _categorySelectedEvent


    fun getCategories() {
        viewModelScope.launch(dispatcher) {
            _showLoadingEvent.triggerEvent()
            runCatching {
                getCategoriesUseCase()
            }.onSuccess {
                 handlerSuccess(it)
            }.onFailure {
                _errorResponseEvent.triggerEvent()
            }
        }
    }

    fun onCategorySelected(category: String) {
        _categorySelectedEvent.triggerEvent(category)
    }

    private fun handlerSuccess(data: ZeldaPresentation) {
        when (data) {
            is ZeldaPresentation.EmptyResponse -> { _emptyResponseEvent.triggerEvent() }
            is ZeldaPresentation.ErrorResponse -> { _errorResponseEvent.triggerEvent() }
            is ZeldaPresentation.CategoriesSuccessResponse -> { _successResponseEvent.triggerPostEvent(data.items) }
            else -> { _emptyResponseEvent.triggerEvent() }
        }
    }
}