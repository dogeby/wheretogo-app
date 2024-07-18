package com.dogeby.wheretogo.core.ui.components.carousel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.card.FestivalCard
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FestivalCardCarousel(
    festivalsState: FestivalListUiState,
    onClickItem: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    pageSpacing: Dp = 0.dp,
) {
    when (festivalsState) {
        FestivalListUiState.Loading -> Unit
        is FestivalListUiState.Success -> {
            val pagerState = rememberPagerState {
                festivalsState.festivals.size
            }
            HorizontalPager(
                state = pagerState,
                modifier = modifier,
                contentPadding = contentPadding,
                pageSize = pageSize,
                pageSpacing = pageSpacing,
            ) {
                with(festivalsState.festivals[it]) {
                    FestivalCard(
                        title = title,
                        imgSrc = imgSrc,
                        startDate = startDate,
                        endDate = endDate,
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
@Preview(showBackground = true)
@Composable
private fun FestivalCardCarouselPreview() {
    FestivalCardCarousel(
        festivalsState = FestivalListUiState.Success(
            contentTypeId = "15",
            contentTypeName = "축제/공연/행사",
            festivals = List(10) {
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
            },
        ),
        onClickItem = {},
        contentPadding = PaddingValues(16.dp),
        pageSize = PageSize.Fixed(360.dp),
        pageSpacing = 16.dp,
    )
}
