package com.dogeby.wheretogo.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val query: StateFlow<String> =
        savedStateHandle
            .getStateFlow(
                key = SEARCH_QUERY_KEY,
                initialValue = "",
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )

    fun editQuery(query: String) {
        savedStateHandle.set(
            key = SEARCH_QUERY_KEY,
            value = query,
        )
    }

    private companion object {

        const val SEARCH_QUERY_KEY = "searchQueryKey"
    }
}
