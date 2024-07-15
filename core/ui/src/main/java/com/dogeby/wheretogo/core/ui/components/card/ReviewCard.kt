package com.dogeby.wheretogo.core.ui.components.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import com.dogeby.wheretogo.core.ui.components.common.ICON_TEXT_DEFAULT_ICON_SIZE
import com.dogeby.wheretogo.core.ui.components.common.IconText
import com.dogeby.wheretogo.core.ui.util.formatDate

private val REVIEW_IMAGE_SIZE = 144.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReviewCardWithWriter(
    writerImgSrc: Any,
    writerName: String,
    reviewDate: String,
    starRating: Int,
    imgSrcs: List<Any>,
    reviewContent: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onImageClick: (currentPage: Int) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.Transparent),
    isWriter: Boolean = false,
) {
    var reviewContentExpanded by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { imgSrcs.size }

    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        Column {
            ReviewWriterHeader(
                writerImgSrc = writerImgSrc,
                writerName = writerName,
                reviewDate = reviewDate,
                starRating = starRating,
                onEdit = onEdit,
                onDelete = onDelete,
                isWriter = isWriter,
            )

            if (pagerState.pageCount != 0) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                    pageSize = PageSize.Fixed(REVIEW_IMAGE_SIZE),
                    pageSpacing = 8.dp,
                ) { page ->
                    Card(onClick = { onImageClick(page) }) {
                        AsyncImageWithFallback(
                            imgSrc = imgSrcs[page],
                            modifier = Modifier.size(REVIEW_IMAGE_SIZE),
                        )
                    }
                }
            }

            if (reviewContent.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                        ) {
                            reviewContentExpanded = !reviewContentExpanded
                        },
                ) {
                    Text(
                        text = reviewContent,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (reviewContentExpanded) Int.MAX_VALUE else 5,
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewWriterHeader(
    writerImgSrc: Any,
    writerName: String,
    reviewDate: String,
    starRating: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    isWriter: Boolean = false,
) {
    Row(
        modifier = modifier.padding(
            start = 16.dp,
            top = 12.dp,
            end = 4.dp,
            bottom = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImageWithFallback(
            imgSrc = writerImgSrc,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = writerName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconText(
                    icon = Icons.Default.CalendarToday,
                    text = reviewDate.formatDate(LocalConfiguration.current.locales[0]),
                )
                Text(
                    text = "•",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                )
                repeat(starRating) {
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = null,
                        modifier = Modifier
                            .size(ICON_TEXT_DEFAULT_ICON_SIZE)
                            .offset(y = (-1).dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        if (isWriter) {
            ReviewMoreBtn(
                onEdit = onEdit,
                onDelete = onDelete,
            )
        }
    }
}

@Composable
private fun ReviewMoreBtn(
    onEdit: (() -> Unit),
    onDelete: (() -> Unit),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart),
    ) {
        var expanded by remember { mutableStateOf(false) }
        var openDialog by remember { mutableStateOf(false) }

        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.edit))
                },
                onClick = {
                    expanded = false
                    onEdit()
                },
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.delete))
                },
                onClick = {
                    expanded = false
                    openDialog = true
                },
            )
        }
        if (openDialog) {
            AlertDialog(
                onDismissRequest = { openDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        openDialog = false
                        onDelete()
                    }) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                },
                text = {
                    Text(text = stringResource(id = R.string.delete_review))
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardWithWriterPreview() {
    ReviewCardWithWriter(
        writerImgSrc = "",
        writerName = "Writer",
        reviewDate = "240611",
        starRating = 4,
        imgSrcs = List(8) {
            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg"
        },
        reviewContent =
        " Gyeongbokgung Palace is the primary palace of the Joseon dynasty.".repeat(5),
        onEdit = {},
        onDelete = {},
        onImageClick = {},
        modifier = Modifier
            .padding(8.dp)
            .width(360.dp),
        colors = CardDefaults.cardColors(),
        isWriter = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardWithWriterPreview_ImgSrcsEmpty() {
    ReviewCardWithWriter(
        writerImgSrc = "",
        writerName = "Writer",
        reviewDate = "240611",
        starRating = 4,
        imgSrcs = emptyList(),
        reviewContent =
        " Gyeongbokgung Palace is the primary palace of the Joseon dynasty.".repeat(5),
        onEdit = {},
        onDelete = {},
        onImageClick = {},
        modifier = Modifier
            .padding(8.dp)
            .width(360.dp),
        colors = CardDefaults.cardColors(),
        isWriter = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardWithWriterPreview_ContentEmpty() {
    ReviewCardWithWriter(
        writerImgSrc = "",
        writerName = "Writer",
        reviewDate = "240611",
        starRating = 4,
        imgSrcs = List(8) {
            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg"
        },
        reviewContent = "",
        onEdit = {},
        onDelete = {},
        onImageClick = {},
        modifier = Modifier
            .padding(8.dp)
            .width(360.dp),
        colors = CardDefaults.cardColors(),
        isWriter = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardWithWriterPreview_ImgSrcsAndContentEmpty() {
    ReviewCardWithWriter(
        writerImgSrc = "",
        writerName = "Writer",
        reviewDate = "240611",
        starRating = 4,
        imgSrcs = emptyList(),
        reviewContent = "",
        onEdit = {},
        onDelete = {},
        onImageClick = {},
        modifier = Modifier
            .padding(8.dp)
            .width(360.dp),
        colors = CardDefaults.cardColors(),
        isWriter = true,
    )
}

@Preview(showBackground = true)
@Composable
private fun ReviewWriterHeaderPreview() {
    ReviewWriterHeader(
        writerImgSrc = "",
        writerName = "Writer",
        reviewDate = "240611",
        starRating = 4,
        onEdit = {},
        onDelete = {},
        modifier = Modifier.width(360.dp),
        isWriter = true,
    )
}
