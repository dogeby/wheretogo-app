package com.dogeby.wheretogo.feature.reviewedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R

@Composable
internal fun ReviewTextField(
    reviewContent: () -> String,
    onReviewContentChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(id = R.string.review_text_field_placeholder),
    limitLength: Int = 500,
) {
    OutlinedTextField(
        value = reviewContent(),
        onValueChange = {
            if (it.length <= limitLength) {
                onReviewContentChanged(it)
            } else {
                onReviewContentChanged(it.substring(0, limitLength))
            }
        },
        modifier = modifier,
        placeholder = {
            Text(text = placeholderText)
        },
        supportingText = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text("${reviewContent().length}/$limitLength")
            }
        },
        isError = reviewContent().length >= limitLength,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewTextFieldPreview() {
    var reviewContent by remember {
        mutableStateOf("")
    }

    ReviewTextField(
        reviewContent = { reviewContent },
        onReviewContentChanged = { reviewContent = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(16.dp),
    )
}
