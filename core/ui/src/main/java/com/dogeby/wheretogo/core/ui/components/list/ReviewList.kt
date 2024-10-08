package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.components.card.ReviewCardWithContent
import com.dogeby.wheretogo.core.ui.components.card.ReviewCardWithWriter
import com.dogeby.wheretogo.core.ui.model.ReviewWithContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ReviewWithContentListUiState
import com.dogeby.wheretogo.core.ui.model.ReviewWithWriterListItemUiState
import com.dogeby.wheretogo.core.ui.model.ReviewWithWriterListUiState

fun LazyListScope.reviewCardWithWriterList(
    reviewWithWriterListUiState: ReviewWithWriterListUiState,
    onEdit: (id: String) -> Unit,
    onDelete: (id: String) -> Unit,
    onImageClick: (index: Int, imgSrcs: List<Any>) -> Unit,
) {
    when (reviewWithWriterListUiState) {
        ReviewWithWriterListUiState.Loading -> Unit
        is ReviewWithWriterListUiState.Success -> {
            items(reviewWithWriterListUiState.reviews) { review ->
                with(review) {
                    ReviewCardWithWriter(
                        writerImgSrc = writerImgSrc,
                        writerName = writerName,
                        reviewDate = reviewDate,
                        starRating = starRating,
                        imgSrcs = imgSrcs,
                        reviewContent = reviewContent,
                        onEdit = {
                            onEdit(id)
                        },
                        onDelete = {
                            onDelete(id)
                        },
                        onImageClick = {
                            onImageClick(it, imgSrcs)
                        },
                        isWriter = isWriter,
                    )
                }
            }
        }
    }
}

fun LazyListScope.reviewCardWithContentList(
    reviewWithContentListUiState: ReviewWithContentListUiState,
    onClickHeader: (contentId: String) -> Unit,
    onEdit: (id: String) -> Unit,
    onDelete: (id: String) -> Unit,
    onImageClick: (index: Int, imgSrcs: List<Any>) -> Unit,
) {
    when (reviewWithContentListUiState) {
        ReviewWithContentListUiState.Loading -> Unit
        is ReviewWithContentListUiState.Success -> {
            items(reviewWithContentListUiState.reviews) { review ->
                with(review) {
                    ReviewCardWithContent(
                        title = title,
                        reviewDate = reviewDate,
                        starRating = starRating,
                        imgSrcs = imgSrcs,
                        reviewContent = reviewContent,
                        onClickHeader = {
                            onClickHeader(contentId)
                        },
                        onEdit = {
                            onEdit(id)
                        },
                        onDelete = {
                            onDelete(id)
                        },
                        onImageClick = {
                            onImageClick(it, imgSrcs)
                        },
                        isWriter = isWriter,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardWithWriterListPreview() {
    LazyColumn {
        reviewCardWithWriterList(
            reviewWithWriterListUiState = ReviewWithWriterListUiState.Success(
                reviews = List(10) {
                    ReviewWithWriterListItemUiState(
                        id = it.toString(),
                        writerImgSrc = "",
                        writerName = "Writer",
                        reviewDate = "240611",
                        starRating = 4,
                        reviewContent =
                        " Gyeongbokgung Palace is the primary palace of the Joseon dynasty.".repeat(
                            5,
                        ),
                        imgSrcs = List(8) {
                            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg"
                        },
                        isWriter = true,
                    )
                },
            ),
            onEdit = {},
            onDelete = {},
            onImageClick = { _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewCardWithContentListPreview() {
    LazyColumn {
        reviewCardWithContentList(
            reviewWithContentListUiState = ReviewWithContentListUiState.Success(
                reviews = List(10) {
                    ReviewWithContentListItemUiState(
                        id = it.toString(),
                        contentId = it.toString(),
                        title = "Title",
                        reviewDate = "240611",
                        starRating = 4,
                        reviewContent =
                        " Gyeongbokgung Palace is the primary palace of the Joseon dynasty.".repeat(
                            5,
                        ),
                        imgSrcs = List(8) {
                            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg"
                        },
                        isWriter = true,
                    )
                },
            ),
            onClickHeader = {},
            onEdit = {},
            onDelete = {},
            onImageClick = { _, _ -> },
        )
    }
}
