package com.dogeby.wheretogo.core.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarActionUiState(
    val icon: ImageVector,
    val contentDescription: String? = null,
    val onActionClick: () -> Unit = {},
)
