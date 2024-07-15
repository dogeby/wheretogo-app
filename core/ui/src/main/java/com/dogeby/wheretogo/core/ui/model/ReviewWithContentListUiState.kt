package com.dogeby.wheretogo.core.ui.model

sealed interface ReviewWithContentListUiState {

    data object Loading : ReviewWithContentListUiState

    data class Success(
        val reviews: List<ReviewWithContentListItemUiState>,
    ) : ReviewWithContentListUiState
}

data class ReviewWithContentListItemUiState(
    val id: String,
    val contentId: String,
    val title: String,
    val reviewDate: String,
    val starRating: Int,
    val reviewContent: String,
    val imgSrcs: List<Any> = emptyList(),
    val isWriter: Boolean = false,
)
