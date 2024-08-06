package com.dogeby.wheretogo.feature.festivals.model

import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState

sealed interface FestivalsScreenUiState {

    data object Loading : FestivalsScreenUiState

    data class Success(
        val categoryChipStates: List<CategoryChipUiState>,
    ) : FestivalsScreenUiState
}
