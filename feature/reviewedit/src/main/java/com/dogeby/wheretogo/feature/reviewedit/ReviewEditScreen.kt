package com.dogeby.wheretogo.feature.reviewedit

import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R

@Composable
internal fun ReviewEditScreen(
    title: String,
    starRating: () -> Int,
    onStarRatingChanged: (starRating: Int) -> Unit,
    reviewContent: () -> String,
    onReviewContentChanged: (String) -> Unit,
    photoSrcs: List<Uri>,
    onAddPhotoSrcs: (uris: List<Uri>) -> Unit,
    onRemovePhotoSrc: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            ),
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            StarRatingSelection(
                starRating = starRating,
                onStarRatingChanged = onStarRatingChanged,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        ReviewTextField(
            reviewContent = reviewContent,
            onReviewContentChanged = onReviewContentChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = R.string.photo_picker_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (Build.VERSION.SDK_INT >= 30) {
            PhotoPickerWithPickVisualMedia(
                photoSrcs = photoSrcs,
                maxItems = 5,
                onAddPhotoSrcs = onAddPhotoSrcs,
                onRemovePhotoSrc = onRemovePhotoSrc,
            )
        } else {
            PhotoPickerWithGetContent(
                photoSrcs = photoSrcs,
                maxItems = 5,
                onAddPhotoSrc = {
                    onAddPhotoSrcs(listOf(it))
                },
                onRemovePhotoSrc = onRemovePhotoSrc,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewEditScreenPreview() {
    ReviewEditScreen(
        title = "Title",
        starRating = { 4 },
        onStarRatingChanged = {},
        reviewContent = { "" },
        onReviewContentChanged = {},
        photoSrcs = emptyList(),
        onAddPhotoSrcs = {},
        onRemovePhotoSrc = {},
    )
}
