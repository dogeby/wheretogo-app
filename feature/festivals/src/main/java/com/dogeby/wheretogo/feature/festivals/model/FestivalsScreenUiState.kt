package com.dogeby.wheretogo.feature.festivals.model

import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

sealed interface FestivalsScreenUiState {

    data object Loading : FestivalsScreenUiState

    data class Success(
        val categoryChipStates: List<CategoryChipUiState>,
        val festivalsState: FestivalListUiState,
    ) : FestivalsScreenUiState
}
