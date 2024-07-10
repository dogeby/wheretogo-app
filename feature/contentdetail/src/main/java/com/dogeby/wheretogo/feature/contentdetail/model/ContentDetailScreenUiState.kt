package com.dogeby.wheretogo.feature.contentdetail.model

import com.dogeby.wheretogo.core.ui.model.ReviewListUiState

sealed interface ContentDetailScreenUiState {

    data object Loading : ContentDetailScreenUiState

    data class Success(
        val id: String,
        val imgSrcs: List<Any>,
        val title: String,
        val avgStarRating: Double,
        val modifiedTime: String,
        val categories: List<String> = emptyList(),
        val overview: String = "",
        val tel: String = "",
        val homepage: String = "",
        val reviewListUiState: ReviewListUiState,
        val ratingFilterOption: RatingFilterOption,
    ) : ContentDetailScreenUiState
}
