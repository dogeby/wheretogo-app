package com.dogeby.wheretogo.feature.contentdetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogeby.wheretogo.core.ui.components.common.ImgHorizontalPager
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.dialogue.ImgDetailDialogue
import com.dogeby.wheretogo.core.ui.model.ReviewWithWriterListItemUiState
import com.dogeby.wheretogo.core.ui.model.ReviewWithWriterListUiState
import com.dogeby.wheretogo.feature.contentdetail.model.ContentDetailScreenUiState
import com.dogeby.wheretogo.feature.contentdetail.model.RatingFilterOption

@Composable
internal fun ContentDetailRoute(
    navigateToReviewCreate: (contentId: String) -> Unit,
    navigateToReviewEdit: (reviewId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContentDetailViewModel = hiltViewModel(),
) {
    val contentDetailScreenUiState by viewModel
        .contentDetailScreenUiState
        .collectAsStateWithLifecycle()

    ContentDetailScreen(
        contentDetailScreenUiState = contentDetailScreenUiState,
        onReviewCreate = navigateToReviewCreate,
        onReviewEdit = navigateToReviewEdit,
        onReviewDelete = {},
        onFilterChanged = {},
        modifier = modifier,
    )
}

@Composable
internal fun ContentDetailScreen(
    contentDetailScreenUiState: ContentDetailScreenUiState,
    onReviewCreate: (contentId: String) -> Unit,
    onReviewEdit: (reviewId: String) -> Unit,
    onReviewDelete: (reviewId: String) -> Unit,
    onFilterChanged: (RatingFilterOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    var imgDetailDialogue by remember {
        mutableStateOf<Pair<Int?, List<Any>>>(null to emptyList())
    }
    when (contentDetailScreenUiState) {
        ContentDetailScreenUiState.Loading -> {
            LoadingDisplay(modifier = modifier)
        }
        is ContentDetailScreenUiState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                with(contentDetailScreenUiState) {
                    if (imgSrcs.isNotEmpty()) {
                        item {
                            ImgHorizontalPager(
                                imgSrcs = imgSrcs,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clip(CardDefaults.shape),
                                onImgClick = { page, imgSrcs ->
                                    imgDetailDialogue = page to imgSrcs
                                },
                            )
                        }
                    }
                    commonContent(
                        title = title,
                        avgStarRating = avgStarRating,
                        modifiedTime = modifiedTime,
                        categories = categories,
                        overview = overview,
                        tel = tel,
                        homepage = homepage,
                    )
                    item {
                        HorizontalDivider()
                    }
                    reviewContent(
                        reviewWithWriterListUiState = reviewWithWriterListUiState,
                        ratingFilterOption = ratingFilterOption,
                        onCreate = {
                            onReviewCreate(id)
                        },
                        onEdit = onReviewEdit,
                        onDelete = onReviewDelete,
                        onImageClick = { page, imgSrcs ->
                            imgDetailDialogue = page to imgSrcs
                        },
                        onFilterChanged = onFilterChanged,
                    )
                }
            }
        }
    }

    imgDetailDialogue.first?.let {
        ImgDetailDialogue(
            initialPage = it,
            imgSrcs = imgDetailDialogue.second,
            onDismissRequest = {
                imgDetailDialogue = null to emptyList()
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentDetailScreenPreview() {
    ContentDetailScreen(
        contentDetailScreenUiState = ContentDetailScreenUiState.Success(
            id = "",
            imgSrcs = List(4) {
                "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg"
            },
            title = "경복궁",
            avgStarRating = 4.5,
            modifiedTime = "20170825173054",
            categories = listOf("cat1", "cat2", "cat3"),
            overview = "경복궁은 1395년태조이성계에의해서새로운조선왕조의법궁으로지어졌다." +
                " 경복궁은동궐(창덕궁)이나서궐(경희궁)에비해위치가북쪽에있어 '북궐'이라불리기도했다.",
            tel = "02-999-9999",
            homepage = "http://www.royalpalace.go.kr",
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
                        isWriter = it % 2 == 0,
                    )
                },
            ),
            ratingFilterOption = RatingFilterOption.FOUR,
        ),
        onReviewCreate = {},
        onReviewEdit = {},
        onReviewDelete = {},
        onFilterChanged = {},
    )
}
