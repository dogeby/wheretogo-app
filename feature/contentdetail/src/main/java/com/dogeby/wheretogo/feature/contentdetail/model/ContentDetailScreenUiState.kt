package com.dogeby.wheretogo.feature.contentdetail.model

import com.dogeby.wheretogo.core.ui.model.ReviewWithWriterListUiState

sealed interface ContentDetailScreenUiState {

    data object Loading : ContentDetailScreenUiState

    data class Success(
        val id: String,
        val title: String,
        val avgStarRating: Double,
        val modifiedTime: String,
        val reviewWithWriterListUiState: ReviewWithWriterListUiState,
        val imgSrcs: List<Any> = emptyList(),
        val categories: List<String> = emptyList(),
        val overview: String = "",
        val tel: String = "",
        val homepage: String = "",
        val address: String = "",
        val longitude: Double? = null,
        val latitude: Double? = null,
        val ratingFilterOption: RatingFilterOption = RatingFilterOption.ALL,
    ) : ContentDetailScreenUiState
}
