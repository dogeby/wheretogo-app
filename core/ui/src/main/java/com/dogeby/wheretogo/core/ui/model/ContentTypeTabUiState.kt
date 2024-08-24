package com.dogeby.wheretogo.core.ui.model

import com.dogeby.wheretogo.core.model.tour.TourContentType

data class ContentTypeTabUiState(
    val contentType: TourContentType,
    val isSelected: Boolean,
)
