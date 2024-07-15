package com.dogeby.wheretogo.core.ui.model

sealed interface ReviewWithWriterListUiState {

    data object Loading : ReviewWithWriterListUiState

    data class Success(
        val reviews: List<ReviewWithWriterListItemUiState>,
    ) : ReviewWithWriterListUiState
}

data class ReviewWithWriterListItemUiState(
    val id: String,
    val writerImgSrc: Any = "",
    val writerName: String,
    val writeDate: String,
    val starRating: Int,
    val reviewContent: String,
    val imgSrcs: List<Any> = emptyList(),
    val isWriter: Boolean = false,
)
