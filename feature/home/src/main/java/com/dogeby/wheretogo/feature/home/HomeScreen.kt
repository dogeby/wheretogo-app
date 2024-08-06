package com.dogeby.wheretogo.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState
import com.dogeby.wheretogo.feature.home.model.HomeScreenUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun HomeScreen(
    homeScreenUiState: HomeScreenUiState,
    festivals: LazyPagingItems<FestivalListItemUiState>,
    contentsList: List<LazyPagingItems<ContentListItemUiState>>,
    onNavigateToList: (contentTypeId: String, areaCode: String, sigunguCode: String) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (homeScreenUiState) {
        HomeScreenUiState.Loading -> {
            LoadingDisplay(modifier = modifier)
        }
        is HomeScreenUiState.Success -> {
            HomeScreenContent(
                homeScreenUiState = homeScreenUiState,
                festivals = festivals,
                contentsList = contentsList,
                onNavigateToList = onNavigateToList,
                onNavigateToContentDetail = onNavigateToContentDetail,
                modifier = modifier,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val festivalListUiState = FestivalListUiState.Success(
        contentTypeId = "15",
        contentTypeName = "축제/공연/행사",
    )
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
    val pagedFestivals = flowOf(PagingData.from(festivals)).collectAsLazyPagingItems()

    val contentListStatesSize = 7
    val contentListUiState = ContentListUiState.Success(
        contentTypeId = "12",
        contentTypeName = "관광지",
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
    val pagedContents = flowOf(PagingData.from(contents)).collectAsLazyPagingItems()

    HomeScreen(
        homeScreenUiState = HomeScreenUiState.Success(
            festivalPerformanceEventListState = festivalListUiState,
            contentListStates = List(contentListStatesSize) {
                contentListUiState
            },
        ),
        festivals = pagedFestivals,
        contentsList = List(contentListStatesSize) { pagedContents },
        onNavigateToList = { _, _, _ -> },
        onNavigateToContentDetail = {},
    )
}
