package com.dogeby.wheretogo.core.ui.model

sealed interface ContentListUiState {

    data object Loading : ContentListUiState

    data class Success(
        val contentTypeId: String,
        val contentTypeName: String,
        val areaCode: String = "",
        val areaName: String = "",
        val sigunguCode: String = "",
        val sigunguName: String = "",
    ) : ContentListUiState
}

data class ContentListItemUiState(
    val id: String,
    val title: String,
    val imgSrc: Any = "",
    val categories: List<String> = emptyList(),
    val avgStarRating: Double = 0.0,
    val areaName: String = "",
    val sigunguName: String = "",
)
