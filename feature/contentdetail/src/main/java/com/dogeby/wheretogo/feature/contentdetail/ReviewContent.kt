package com.dogeby.wheretogo.feature.contentdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.list.reviewCardList
import com.dogeby.wheretogo.core.ui.model.ReviewListItemUiState
import com.dogeby.wheretogo.core.ui.model.ReviewListUiState
import com.dogeby.wheretogo.feature.contentdetail.model.RatingFilterOption

internal fun LazyListScope.reviewContent(
    reviewListUiState: ReviewListUiState,
    ratingFilterOption: RatingFilterOption,
    onCreate: () -> Unit,
    onEdit: (id: String) -> Unit,
    onDelete: (id: String) -> Unit,
    onImageClick: (index: Int, imgSrcs: List<Any>) -> Unit,
    onFilterChanged: (RatingFilterOption) -> Unit,
) {
    item {
        Column {
            ListItem(
                headlineContent = {
                    Text(
                        text = stringResource(id = R.string.review),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                trailingContent = {
                    IconButton(onClick = onCreate) {
                        Icon(
                            imageVector = Icons.Default.BorderColor,
                            contentDescription = null,
                        )
                    }
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(RatingFilterOption.entries.size) {
                    FilterChip(
                        selected = ratingFilterOption.rating == it,
                        onClick = { onFilterChanged(RatingFilterOption.entries[it]) },
                        label = {
                            Text(
                                when (it) {
                                    0 -> {
                                        stringResource(id = R.string.all)
                                    }
                                    else -> {
                                        "$it ★"
                                    }
                                },
                            )
                        },
                    )
                }
            }
        }
    }
    reviewCardList(
        reviewListUiState = reviewListUiState,
        onEdit = onEdit,
        onDelete = onDelete,
        onImageClick = onImageClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewContentPreview() {
    LazyColumn {
        reviewContent(
            reviewListUiState = ReviewListUiState.Success(
                reviews = List(10) {
                    ReviewListItemUiState(
                        id = it.toString(),
                        writerImgSrc = "",
                        writerName = "Writer",
                        writeDate = "240611",
                        starRating = 4,
                        reviewContent =
                        " Gyeongbokgung Palace is the primary palace of the Joseon dynasty.".repeat(
                            5,
                        ),
                        imgSrcs = List(8) {
                            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg"
                        },
                        isWriter = it % 2 == 0,
                    )
                },
            ),
            ratingFilterOption = RatingFilterOption.TWO,
            onCreate = {},
            onEdit = {},
            onDelete = {},
            onImageClick = { _, _ -> },
            onFilterChanged = {},
        )
    }
}
