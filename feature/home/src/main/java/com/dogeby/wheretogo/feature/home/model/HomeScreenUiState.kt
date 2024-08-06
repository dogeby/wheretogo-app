package com.dogeby.wheretogo.feature.home.model

import com.dogeby.wheretogo.core.ui.model.ContentListUiState

sealed interface HomeScreenUiState {

    data object Loading : HomeScreenUiState

    data class Success(
        val contentListStates: List<ContentListUiState>,
    ) : HomeScreenUiState
}
