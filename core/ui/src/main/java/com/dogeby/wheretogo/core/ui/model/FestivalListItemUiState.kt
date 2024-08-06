package com.dogeby.wheretogo.core.ui.model

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
