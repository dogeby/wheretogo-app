package com.dogeby.wheretogo.core.ui.components.model

sealed interface ContentListUiState {

    data object Loading : ContentListUiState

    data class Success(
        val contents: List<ContentListItemUiState>,
    ) : ContentListUiState
}

data class ContentListItemUiState(
    val id: String,
    val title: String,
    val imgSrc: Any,
    val categories: List<String>,
    val avgStarRating: Double,
    val areaName: String,
    val sigunguName: String,
)
