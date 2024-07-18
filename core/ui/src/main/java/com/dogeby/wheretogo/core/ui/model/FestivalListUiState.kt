package com.dogeby.wheretogo.core.ui.model

sealed interface FestivalListUiState {

    data object Loading : FestivalListUiState

    data class Success(
        val contentTypeId: String,
        val contentTypeName: String,
        val festivals: List<FestivalListItemUiState>,
    ) : FestivalListUiState
}

data class FestivalListItemUiState(
    val id: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imgSrc: Any = "",
    val avgStarRating: Double = 0.0,
    val areaName: String = "",
    val sigunguName: String = "",
)
