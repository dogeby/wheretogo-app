package com.dogeby.wheretogo.core.ui.components.carousel

import androidx.annotation.IntRange
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.card.ContentCard
import com.dogeby.wheretogo.core.ui.components.listitem.ContentListItem
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentCardCarousel(
    contentsState: ContentListUiState,
    onClickItem: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    pageSpacing: Dp = 0.dp,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            val pagerState = rememberPagerState {
                contentsState.contents.size
            }
            HorizontalPager(
                state = pagerState,
                modifier = modifier,
                contentPadding = contentPadding,
                pageSize = pageSize,
                pageSpacing = pageSpacing,
            ) {
                with(contentsState.contents[it]) {
                    ContentCard(
                        title = title,
                        imgSrc = imgSrc,
                        categories = categories,
                        avgStarRating = avgStarRating,
                        areaName = areaName,
                        sigunguName = sigunguName,
                        onClick = {
                            onClickItem(id)
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentCarousel(
    contentsState: ContentListUiState,
    onClickItem: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    @IntRange(from = 1) columns: Int = 1,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            val pagerState = rememberPagerState {
                (contentsState.contents.size + columns - 1) / columns
            }
            HorizontalPager(
                state = pagerState,
                modifier = modifier,
                contentPadding = contentPadding,
                pageSize = pageSize,
                verticalAlignment = verticalAlignment,
                pageSpacing = pageSpacing,
            ) { page ->
                val startIndex = page * columns
                val endExclusiveIndex = minOf(startIndex + columns, contentsState.contents.size)

                Column {
                    (startIndex until endExclusiveIndex).forEach {
                        with(contentsState.contents[it]) {
                            ContentListItem(
                                title = title,
                                imgSrc = imgSrc,
                                categories = categories,
                                avgStarRating = avgStarRating,
                                areaName = areaName,
                                sigunguName = sigunguName,
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                ) {
                                    onClickItem(id)
                                },
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun ContentCardCarouselPreview() {
    ContentCardCarousel(
        contentsState = ContentListUiState.Success(
            contentTypeId = "12",
            contentTypeName = "관광지",
            contents = List(10) {
                ContentListItemUiState(
                    id = "$it",
                    title = "Title",
                    imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/" +
                        "2678623_image3_1.jpg",
                    categories = listOf("cat1", "cat2", "cat3"),
                    avgStarRating = 4.5,
                    areaName = "area",
                    sigunguName = "sigungu",
                )
            },
        ),
        onClickItem = {},
        contentPadding = PaddingValues(16.dp),
        pageSize = PageSize.Fixed(240.dp),
        pageSpacing = 16.dp,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun ContentCarouselPreview() {
    ContentCarousel(
        contentsState = ContentListUiState.Success(
            contentTypeId = "12",
            contentTypeName = "관광지",
            contents = List(10) {
                ContentListItemUiState(
                    id = "$it",
                    title = "Title $it",
                    imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/" +
                        "2678623_image3_1.jpg",
                    categories = listOf("cat1", "cat2", "cat3"),
                    avgStarRating = 4.5,
                    areaName = "area",
                    sigunguName = "sigungu",
                )
            },
        ),
        onClickItem = {},
        contentPadding = PaddingValues(16.dp),
        pageSize = PageSize.Fixed(360.dp),
        verticalAlignment = Alignment.Top,
        columns = 3,
    )
}
