package com.dogeby.wheretogo.feature.contents.model

import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState

sealed interface ContentsScreenUiState {

    data object Loading : ContentsScreenUiState

    data class Success(
        val pageStates: List<ContentsPageUiState>,
    ) : ContentsScreenUiState
}

data class ContentsPageUiState(
    val contentTypeTabState: ContentTypeTabUiState,
    val categoryChipStates: List<CategoryChipUiState>,
)
