package com.dogeby.wheretogo.core.ui.model

sealed interface RegionListUiState {

    data object Loading : RegionListUiState

    data class Success(
        val regions: List<RegionListItemUiState>,
    ) : RegionListUiState
}

data class RegionListItemUiState(
    val code: String,
    val name: String,
    val imgSrc: Any = "",
)
