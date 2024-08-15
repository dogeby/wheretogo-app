package com.dogeby.wheretogo.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.components.carousel.ContentCardCarousel
import com.dogeby.wheretogo.core.ui.components.carousel.ContentCarousel
import com.dogeby.wheretogo.core.ui.components.carousel.FestivalCardCarousel
import com.dogeby.wheretogo.core.ui.components.common.ListTitleRow
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.util.buildLocationContentTypeText
import com.dogeby.wheretogo.core.ui.util.getDisplayName
import com.dogeby.wheretogo.feature.home.model.HomeScreenUiState
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreenContent(
    homeScreenUiState: HomeScreenUiState.Success,
    festivals: LazyPagingItems<FestivalListItemUiState>,
    contentsList: List<LazyPagingItems<ContentListItemUiState>>,
    onNavigateToList: (contentType: TourContentType, areaCode: String, sigunguCode: String) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(
            key = createKey(HomeScreenContentUiType.FestivalCardCarousel),
            contentType = HomeScreenContentUiType.FestivalCardCarousel,
        ) {
            FestivalCardCarousel(
                festivals = festivals,
                onClickItem = onNavigateToContentDetail,
                contentPadding = PaddingValues(horizontal = 16.dp),
                pageSize = PageSize.Fixed(360.dp),
                pageSpacing = 16.dp,
            )
        }
        item(
            contentType = HomeScreenContentUiType.NavigationMenus,
            key = HomeScreenContentUiType.NavigationMenus,
        ) {
            NavigationMenus(
                onNavigateToList = {
                    onNavigateToList(it, "", "")
                },
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
        homeScreenUiState.contentListStates.forEachIndexed { index, contentListUiState ->
            if (index % 3 == 1) {
                contentCardsWithTitleRow(
                    contentsState = contentListUiState,
                    contents = contentsList[index],
                    onNavigateToList = onNavigateToList,
                    onNavigateToContentDetail = onNavigateToContentDetail,
                )
            } else {
                contentsWithTitleRow(
                    contentsState = contentListUiState,
                    contents = contentsList[index],
                    onNavigateToContents = onNavigateToList,
                    onNavigateToContentDetail = onNavigateToContentDetail,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.contentCardsWithTitleRow(
    contentsState: ContentListUiState,
    contents: LazyPagingItems<ContentListItemUiState>,
    onNavigateToList: (contentType: TourContentType, areaCode: String, sigunguCode: String) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            item(
                key = createKey(
                    contentUiType = HomeScreenContentUiType.ListTitleRow,
                    contentListState = contentsState,
                ),
            ) {
                with(contentsState) {
                    ListTitleRow(
                        title = buildLocationContentTypeText(
                            areaName = areaName,
                            sigunguName = sigunguName,
                            contentTypeName = contentType.getDisplayName(),
                        ),
                        onClick = {
                            onNavigateToList(contentType, areaCode, sigunguCode)
                        },
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }

            item(
                key = createKey(
                    contentUiType = HomeScreenContentUiType.ContentCardCarousel,
                    contentListState = contentsState,
                ),
                contentType = HomeScreenContentUiType.ContentCardCarousel,
            ) {
                ContentCardCarousel(
                    contents = contents,
                    onClickItem = onNavigateToContentDetail,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    pageSize = PageSize.Fixed(240.dp),
                    pageSpacing = 16.dp,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.contentsWithTitleRow(
    contentsState: ContentListUiState,
    contents: LazyPagingItems<ContentListItemUiState>,
    onNavigateToContents: (
        contentType: TourContentType,
        areaCode: String,
        sigunguCode: String,
    ) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            item(
                key = createKey(
                    contentUiType = HomeScreenContentUiType.ListTitleRow,
                    contentListState = contentsState,
                ),
                contentType = HomeScreenContentUiType.ListTitleRow,
            ) {
                with(contentsState) {
                    ListTitleRow(
                        title = buildLocationContentTypeText(
                            areaName = areaName,
                            sigunguName = sigunguName,
                            contentTypeName = contentType.getDisplayName(),
                        ),
                        onClick = {
                            onNavigateToContents(contentType, areaCode, sigunguCode)
                        },
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }
            item(
                key = createKey(
                    contentUiType = HomeScreenContentUiType.ContentCarousel,
                    contentListState = contentsState,
                ),
                contentType = HomeScreenContentUiType.ContentCarousel,
            ) {
                ContentCarousel(
                    contents = contents,
                    onClickItem = onNavigateToContentDetail,
                    pageSize = PageSize.Fixed(360.dp),
                    verticalAlignment = Alignment.Top,
                    columns = 3,
                )
            }
        }
    }
}

private enum class HomeScreenContentUiType {
    FestivalCardCarousel,
    NavigationMenus,
    ContentCardCarousel,
    ContentCarousel,
    ListTitleRow,
}

private fun createKey(
    contentUiType: HomeScreenContentUiType,
    contentListState: ContentListUiState.Success? = null,
): String {
    return contentListState?.let {
        "${it.contentType}${it.areaCode}${it.sigunguCode}$contentUiType"
    } ?: "$contentUiType"
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
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

    val contentListStatesSize = 7
    val contentListUiState = ContentListUiState.Success(
        contentType = TourContentType.TouristSpot,
    )
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

    HomeScreenContent(
        homeScreenUiState = HomeScreenUiState.Success(
            contentListStates = List(7) {
                contentListUiState
            },
        ),
        festivals = pagedFestivals,
        contentsList = List(contentListStatesSize) { pagedContents },
        onNavigateToList = { _, _, _ -> },
        onNavigateToContentDetail = {},
    )
}
