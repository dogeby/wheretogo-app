package com.dogeby.wheretogo.core.ui.components.carousel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.components.card.FESTIVAL_CARD_DEFAULT_ASPECT_RATIO
import com.dogeby.wheretogo.core.ui.components.card.FestivalCard
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FestivalCardCarousel(
    festivals: LazyPagingItems<FestivalListItemUiState>,
    onClickItem: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    pageSpacing: Dp = 0.dp,
) {
    val pagerState = rememberPagerState {
        festivals.itemCount
    }
    if (festivals.loadState.refresh == LoadState.Loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .aspectRatio(FESTIVAL_CARD_DEFAULT_ASPECT_RATIO)
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
        festivals[index]?.let {
            FestivalCard(
                title = it.title,
                imgSrc = it.imgSrc,
                startDate = it.startDate,
                endDate = it.endDate,
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
@Preview(showBackground = true)
@Composable
private fun FestivalCardCarouselPreview() {
    val festivals = List(10) {
        FestivalListItemUiState(
            id = "$it",
            title = "Title",
            startDate = "20210306",
            endDate = "20211030",
            imgSrc = "http://tong.visitkorea.or.kr/cms/resource/54/" +
                "2483454_image2_1.JPG",
            avgStarRating = 4.5,
            areaName = "area",
            sigunguName = "sigungu",
        )
    }
    val pagedFestivals = flowOf(
        PagingData.from(
            data = festivals,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            ),
        ),
    ).collectAsLazyPagingItems()

    FestivalCardCarousel(
        festivals = pagedFestivals,
        onClickItem = {},
        contentPadding = PaddingValues(16.dp),
        pageSize = PageSize.Fixed(360.dp),
        pageSpacing = 16.dp,
    )
}
