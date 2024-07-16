package com.dogeby.wheretogo.feature.profileedit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.R

@Composable
internal fun NicknameTextField(
    originalNickname: String,
    inputNickname: () -> String,
    onInputNicknameChanged: (String) -> Unit,
    onClearInputNickname: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    OutlinedTextField(
        value = inputNickname(),
        onValueChange = {
            onInputNicknameChanged(it)
        },
        modifier = modifier,
        placeholder = {
            Text(text = originalNickname)
        },
        trailingIcon = {
            IconButton(onClick = onClearInputNickname) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = null,
                )
            }
        },
        supportingText = {
            Text(stringResource(id = R.string.nickname_text_field_supporting_text))
        },
        isError = isError,
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun NicknameTextFieldPreview() {
    var inputNickname by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }

    NicknameTextField(
        originalNickname = "Nickname",
        inputNickname = { inputNickname },
        onInputNicknameChanged = {
            inputNickname = it
            isError = (2..10).contains(inputNickname.length).not()
        },
        onClearInputNickname = { inputNickname = "" },
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
    )
}
