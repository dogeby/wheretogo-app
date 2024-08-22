package com.dogeby.wheretogo.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.feature.home.model.HomeScreenUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun HomeRoute(
    navigateToList: (
        contentType: TourContentType,
        areaCode: String?,
        sigunguCode: String?,
    ) -> Unit,
    navigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeScreenUiState = viewModel.homeScreenUiState
    val festivals = viewModel.festivalPerformanceEventListState.collectAsLazyPagingItems()
    val contentsList = viewModel.contentListStates.map {
        it.collectAsLazyPagingItems()
    }
    HomeScreen(
        homeScreenUiState = homeScreenUiState,
        festivals = festivals,
        contentsList = contentsList,
        onNavigateToList = navigateToList,
        onNavigateToContentDetail = navigateToContentDetail,
        modifier = modifier,
    )
}

@Composable
internal fun HomeScreen(
    homeScreenUiState: HomeScreenUiState,
    festivals: LazyPagingItems<FestivalListItemUiState>,
    contentsList: List<LazyPagingItems<ContentListItemUiState>>,
    onNavigateToList: (
        contentType: TourContentType,
        areaCode: String?,
        sigunguCode: String?,
    ) -> Unit,
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

    HomeScreen(
        homeScreenUiState = HomeScreenUiState.Success(
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
