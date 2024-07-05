package com.dogeby.wheretogo.feature.searchresult.model

import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

sealed interface SearchResultScreenUiState {

    data object Loading : SearchResultScreenUiState

    data object Failure : SearchResultScreenUiState

    data object Empty : SearchResultScreenUiState

    data class Success(
        val attractionListState: ContentListUiState,
        val festivalListState: FestivalListUiState,
        val restaurantListState: ContentListUiState,
        val accommodationListState: ContentListUiState,
    ) : SearchResultScreenUiState
}
