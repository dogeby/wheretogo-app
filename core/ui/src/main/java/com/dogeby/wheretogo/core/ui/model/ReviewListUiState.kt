package com.dogeby.wheretogo.core.ui.model

sealed interface ReviewListUiState {

    data object Loading : ReviewListUiState

    data class Success(
        val reviews: List<ReviewListItemUiState>,
    ) : ReviewListUiState
}

data class ReviewListItemUiState(
    val id: String,
    val writerImgSrc: Any = "",
    val writerName: String,
    val writeDate: String,
    val starRating: Int,
    val reviewContent: String,
    val imgSrcs: List<Any> = emptyList(),
    val isWriter: Boolean = false,
)
