package com.dogeby.wheretogo.feature.profileedit.model

sealed interface ProfileEditScreenUiState {

    data object Loading : ProfileEditScreenUiState

    data class Success(
        val originalNickname: String,
        val originalProfileImgSrc: Any = "",
    ) : ProfileEditScreenUiState
}
