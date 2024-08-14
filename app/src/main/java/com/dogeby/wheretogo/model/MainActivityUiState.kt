package com.dogeby.wheretogo.model

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data object Failure : MainActivityUiState
    data object Success : MainActivityUiState
}
