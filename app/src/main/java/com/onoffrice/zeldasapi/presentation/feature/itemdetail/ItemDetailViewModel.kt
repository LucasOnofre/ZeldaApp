package com.onoffrice.zeldasapi.presentation.feature.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onoffrice.zeldasapi.presentation.model.ItemPresentation
import com.onoffrice.zeldasapi.utils.Event
import com.onoffrice.zeldasapi.utils.triggerEvent

internal class ItemDetailViewModel(
) : ViewModel() {

    private val _showItemDetailEvent = MutableLiveData<Event<ItemPresentation>>()

    val showItemDetailEvent: LiveData<Event<ItemPresentation>>
        get() = _showItemDetailEvent

    fun handleItemDetail(item: ItemPresentation) {
        _showItemDetailEvent.triggerEvent(item)
    }
}