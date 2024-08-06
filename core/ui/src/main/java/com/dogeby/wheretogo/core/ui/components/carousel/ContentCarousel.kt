package com.dogeby.wheretogo.core.ui.components.carousel

import androidx.annotation.IntRange
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.components.card.CONTENT_CARD_DEFAULT_ASPECT_RATIO
import com.dogeby.wheretogo.core.ui.components.card.ContentCard
import com.dogeby.wheretogo.core.ui.components.listitem.ContentListItem
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentCardCarousel(
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickItem: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    pageSpacing: Dp = 0.dp,
) {
    val pagerState = rememberPagerState {
        contents.itemCount
    }
    if (contents.loadState.refresh is LoadState.Loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .aspectRatio(CONTENT_CARD_DEFAULT_ASPECT_RATIO)
                .wrapContentSize(Alignment.Center),
        )
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = contentPadding,
        pageSize = pageSize,
        pageSpacing = pageSpacing,
    ) { index ->
        contents[index]?.let {
            ContentCard(
                title = it.title,
                imgSrc = it.imgSrc,
                categories = it.categories,
                avgStarRating = it.avgStarRating,
                areaName = it.areaName,
                sigunguName = it.sigunguName,
                onClick = {
                    onClickItem(it.id)
                },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentCarousel(
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickItem: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    @IntRange(from = 1) columns: Int = 1,
) {
    val pagerState = rememberPagerState {
        (contents.itemCount + columns - 1) / columns
    }
    if (contents.loadState.refresh is LoadState.Loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .aspectRatio(CONTENT_CARD_DEFAULT_ASPECT_RATIO)
                .wrapContentSize(Alignment.Center),
        )
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
        val endExclusiveIndex = minOf(startIndex + columns, contents.itemCount)

        Column {
            (startIndex until endExclusiveIndex).forEach { index ->
                contents[index]?.let {
                    ContentListItem(
                        title = it.title,
                        imgSrc = it.imgSrc,
                        categories = it.categories,
                        avgStarRating = it.avgStarRating,
                        areaName = it.areaName,
                        sigunguName = it.sigunguName,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                        ) {
                            onClickItem(it.id)
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

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun ContentCardCarouselPreview() {
    val contents = List(10) {
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
    }
    val pagedContents = flowOf(
        PagingData.from(
            data = contents,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
        ),
    ).collectAsLazyPagingItems()

    ContentCardCarousel(
        contents = pagedContents,
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
    val contents = List(10) {
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
    }
    val pagedContents = flowOf(
        PagingData.from(
            data = contents,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
        ),
    ).collectAsLazyPagingItems()

    ContentCarousel(
        contents = pagedContents,
        onClickItem = {},
        contentPadding = PaddingValues(16.dp),
        pageSize = PageSize.Fixed(360.dp),
        verticalAlignment = Alignment.Top,
        columns = 3,
    )
}
