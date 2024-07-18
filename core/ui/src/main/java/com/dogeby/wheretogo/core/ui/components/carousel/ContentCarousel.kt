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
import com.dogeby.wheretogo.core.ui.components.card.ContentCard
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
