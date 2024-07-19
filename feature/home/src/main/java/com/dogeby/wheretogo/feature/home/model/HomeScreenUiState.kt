package com.dogeby.wheretogo.feature.home.model

import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

sealed interface HomeScreenUiState {

    data object Loading : HomeScreenUiState

    data class Success(
        val festivalPerformanceEventListState: FestivalListUiState,
        val touristSpotListState: ContentListUiState,
        val culturalFacilityListState: ContentListUiState,
        val leisureSpotListState: ContentListUiState,
        val restaurantListState: ContentListUiState,
        val accommodationListState: ContentListUiState,
    ) : HomeScreenUiState
}
