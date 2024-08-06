package com.dogeby.wheretogo.feature.searchresult

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.common.NoSearchResultsDisplay
import com.dogeby.wheretogo.core.ui.components.list.contentList
import com.dogeby.wheretogo.core.ui.components.list.festivalList
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState
import com.dogeby.wheretogo.feature.searchresult.model.SearchResultScreenUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun SearchResultScreen(
    searchResultScreenState: SearchResultScreenUiState,
    festivals: LazyPagingItems<FestivalListItemUiState>,
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (searchResultScreenState) {
        SearchResultScreenUiState.Empty -> {
            NoSearchResultsDisplay(modifier = modifier)
        }
        SearchResultScreenUiState.Failure -> TODO()
        SearchResultScreenUiState.Loading -> LoadingDisplay()
        is SearchResultScreenUiState.Success -> {
            with(searchResultScreenState) {
                SearchResultList(
                    festivalListState = festivalListState,
                    festivals = festivals,
                    contents = contents,
                    onClickContent = onClickContent,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun SearchResultList(
    festivalListState: FestivalListUiState,
    festivals: LazyPagingItems<FestivalListItemUiState>,
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        contentSearchResultList(
            titleResId = R.string.destination,
            contents = contents,
            onClickContent = onClickContent,
        )
        when (festivalListState) {
            FestivalListUiState.Loading -> Unit
            is FestivalListUiState.Success -> {
                if (festivals.itemCount != 0) {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.festival_performance_event),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    festivalList(
                        festivalsState = festivalListState,
                        festivals = festivals,
                        onClickItem = onClickContent,
                    )
                }
            }
        }
        contentSearchResultList(
            titleResId = R.string.restaurant,
            contents = contents,
            onClickContent = onClickContent,
        )
        contentSearchResultList(
            titleResId = R.string.accommodation,
            contents = contents,
            onClickContent = onClickContent,
        )
    }
}

private fun LazyGridScope.contentSearchResultList(
    @StringRes titleResId: Int,
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickContent: (id: String) -> Unit,
) {
    if (contents.itemCount != 0) {
        item(
            span = {
                GridItemSpan(maxLineSpan)
            },
        ) {
            Text(
                text = stringResource(id = titleResId),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
        contentList(
            contents = contents,
            onClickItem = onClickContent,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Empty() {
    val festivals = emptyList<FestivalListItemUiState>()
    val pagedFestivals = flowOf(PagingData.from(festivals)).collectAsLazyPagingItems()

    val contents = emptyList<ContentListItemUiState>()
    val pagedContents = flowOf(PagingData.from(contents)).collectAsLazyPagingItems()

    SearchResultScreen(
        searchResultScreenState = SearchResultScreenUiState.Empty,
        festivals = pagedFestivals,
        contents = pagedContents,
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Loading() {
    val festivals = emptyList<FestivalListItemUiState>()
    val pagedFestivals = flowOf(PagingData.from(festivals)).collectAsLazyPagingItems()

    val contents = emptyList<ContentListItemUiState>()
    val pagedContents = flowOf(PagingData.from(contents)).collectAsLazyPagingItems()

    SearchResultScreen(
        searchResultScreenState = SearchResultScreenUiState.Loading,
        festivals = pagedFestivals,
        contents = pagedContents,
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Success() {
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

    Column {
        SearchResultScreen(
            searchResultScreenState = SearchResultScreenUiState.Success(
                attractionListState = ContentListUiState.Success(
                    contentTypeId = "12",
                    contentTypeName = "관광지",
                ),
                festivalListState = FestivalListUiState.Success(
                    contentTypeId = "15",
                    contentTypeName = "축제/공연/행사",
                ),
                restaurantListState = ContentListUiState.Success(
                    contentTypeId = "39",
                    contentTypeName = "음식",
                ),
                accommodationListState = ContentListUiState.Success(
                    contentTypeId = "32",
                    contentTypeName = "숙박",
                ),
            ),
            festivals = pagedFestivals,
            contents = pagedContents,
            onClickContent = {},
        )
    }
}
