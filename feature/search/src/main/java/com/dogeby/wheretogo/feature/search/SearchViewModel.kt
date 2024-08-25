package com.dogeby.wheretogo.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.wheretogo.core.domain.searchkeyword.GetRecentSearchKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SearchViewModel @Inject constructor(
    getRecentSearchKeywordUseCase: GetRecentSearchKeywordUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val query: StateFlow<String> =
        savedStateHandle.getStateFlow(
            key = SEARCH_QUERY_KEY,
            initialValue = "",
        )

    val recentQueries: StateFlow<List<String>> = getRecentSearchKeywordUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
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
