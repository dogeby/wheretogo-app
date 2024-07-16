package com.dogeby.wheretogo.feature.profileedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.feature.profileedit.model.ProfileEditScreenUiState

@Composable
internal fun ProfileEditScreen(
    profileEditScreenUiState: ProfileEditScreenUiState,
    inputNickname: () -> String,
    isInputNicknameError: Boolean,
    newProfileImgSrc: Any?,
    onInputNicknameChanged: (String) -> Unit,
    onClearInputNickname: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (profileEditScreenUiState) {
        ProfileEditScreenUiState.Loading -> {
            LoadingDisplay(modifier)
        }
        is ProfileEditScreenUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 16.dp,
                        top = 64.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImageWithFallback(
                    imgSrc = newProfileImgSrc ?: profileEditScreenUiState.originalProfileImgSrc,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.height(64.dp))
                NicknameTextField(
                    originalNickname = profileEditScreenUiState.originalNickname,
                    inputNickname = inputNickname,
                    onInputNicknameChanged = onInputNicknameChanged,
                    onClearInputNickname = onClearInputNickname,
                    isError = isInputNicknameError,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditScreenPreview() {
    var inputNickname by remember {
        mutableStateOf("")
    }
    var isInputNicknameError by remember {
        mutableStateOf(false)
    }

    ProfileEditScreen(
        profileEditScreenUiState = ProfileEditScreenUiState.Success("Nickname"),
        inputNickname = { inputNickname },
        isInputNicknameError = isInputNicknameError,
        newProfileImgSrc = "",
        onInputNicknameChanged = {
            inputNickname = it
            isInputNicknameError = (2..10).contains(inputNickname.length).not()
        },
        onClearInputNickname = { inputNickname = "" },
    )
}
