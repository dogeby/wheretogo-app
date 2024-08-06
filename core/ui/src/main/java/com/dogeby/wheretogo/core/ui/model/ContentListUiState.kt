package com.dogeby.wheretogo.core.ui.model

import com.dogeby.wheretogo.core.model.tour.TourContentType

sealed interface ContentListUiState {

    data object Loading : ContentListUiState

    data class Success(
        val contentType: TourContentType,
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
