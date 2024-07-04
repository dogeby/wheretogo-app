package com.dogeby.wheretogo.feature.search.model

import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

sealed interface SearchScreenUiState {

    val searchBarState: TotalSearchBarUiState

    data class Loading(
        override val searchBarState: TotalSearchBarUiState,
    ) : SearchScreenUiState

    data class Failure(
        val searchKeyword: String,
        override val searchBarState: TotalSearchBarUiState,
    ) : SearchScreenUiState

    data class Success(
        val searchKeyword: String,
        override val searchBarState: TotalSearchBarUiState,
        val attractionListState: ContentListUiState,
        val festivalListState: FestivalListUiState,
        val restaurantListState: ContentListUiState,
        val accommodationListState: ContentListUiState,
    ) : SearchScreenUiState
}

data class TotalSearchBarUiState(
    val query: String = "",
    val recentQueries: List<String> = emptyList(),
)
