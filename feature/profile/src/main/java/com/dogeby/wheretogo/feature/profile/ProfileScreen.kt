package com.dogeby.wheretogo.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.dialogue.ImgDetailDialogue
import com.dogeby.wheretogo.core.ui.components.list.reviewCardWithContentList
import com.dogeby.wheretogo.core.ui.components.listitem.ProfileListItem
import com.dogeby.wheretogo.core.ui.model.ReviewWithContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ReviewWithContentListUiState
import com.dogeby.wheretogo.feature.profile.model.ProfileScreenUiState

@Composable
internal fun ProfileScreen(
    profileScreenUiState: ProfileScreenUiState,
    onNavigateToContentDetail: (contentId: String) -> Unit,
    onReviewEdit: (reviewId: String) -> Unit,
    onReviewDelete: (reviewId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var imgDetailDialogue by remember {
        mutableStateOf<Pair<Int?, List<Any>>>(null to emptyList())
    }

    when (profileScreenUiState) {
        ProfileScreenUiState.Loading -> {
            LoadingDisplay(modifier = modifier)
        }
        is ProfileScreenUiState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                with(profileScreenUiState) {
                    item {
                        ProfileListItem(
                            imgSrc = imgSrc,
                            nickname = nickname,
                            totalReviewCount = totalReviewCount,
                            avgStarRating = avgStarRating,
                            nicknameStyle = MaterialTheme.typography.headlineMedium,
                        )
                        HorizontalDivider()
                    }

                    item {
                        Column {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stringResource(id = R.string.my_review),
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                },
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent,
                                ),
                            )
                        }
                    }
                    reviewCardWithContentList(
                        reviewWithContentListUiState = reviewWithContentListUiState,
                        onClickHeader = onNavigateToContentDetail,
                        onEdit = onReviewEdit,
                        onDelete = onReviewDelete,
                        onImageClick = { page, imgSrcs ->
                            imgDetailDialogue = page to imgSrcs
                        },
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
private fun ProfileScreenPreview() {
    ProfileScreen(
        profileScreenUiState = ProfileScreenUiState.Success(
            imgSrc = "",
            nickname = "nickname",
            totalReviewCount = 5,
            avgStarRating = 4.5,
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
        ),
        onNavigateToContentDetail = {},
        onReviewEdit = {},
        onReviewDelete = {},
    )
}
