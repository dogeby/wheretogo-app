package com.dogeby.wheretogo.feature.profile.model

import com.dogeby.wheretogo.core.ui.model.ReviewWithContentListUiState

sealed interface ProfileScreenUiState {

    data object Loading : ProfileScreenUiState

    data class Success(
        val imgSrc: Any,
        val nickname: String,
        val totalReviewCount: Int = 0,
        val avgStarRating: Double = 0.0,
        val reviewWithContentListUiState: ReviewWithContentListUiState,
    ) : ProfileScreenUiState
}
