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

@Composable
internal fun SearchResultScreen(
    searchResultScreenState: SearchResultScreenUiState,
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
                    attractionListState = attractionListState,
                    festivalListState = festivalListState,
                    restaurantListState = restaurantListState,
                    accommodationListState = accommodationListState,
                    onClickContent = onClickContent,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun SearchResultList(
    attractionListState: ContentListUiState,
    festivalListState: FestivalListUiState,
    restaurantListState: ContentListUiState,
    accommodationListState: ContentListUiState,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        contentSearchResultList(
            titleResId = R.string.attraction,
            contentListState = attractionListState,
            onClickContent = onClickContent,
        )
        when (festivalListState) {
            FestivalListUiState.Loading -> Unit
            is FestivalListUiState.Success -> {
                if (festivalListState.festivals.isNotEmpty()) {
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.festival),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    festivalList(
                        festivalsState = festivalListState,
                        onClickItem = onClickContent,
                    )
                }
            }
        }
        contentSearchResultList(
            titleResId = R.string.restaurant,
            contentListState = restaurantListState,
            onClickContent = onClickContent,
        )
        contentSearchResultList(
            titleResId = R.string.accommodation,
            contentListState = accommodationListState,
            onClickContent = onClickContent,
        )
    }
}

private fun LazyGridScope.contentSearchResultList(
    @StringRes titleResId: Int,
    contentListState: ContentListUiState,
    onClickContent: (id: String) -> Unit,
) {
    when (contentListState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            if (contentListState.contents.isNotEmpty()) {
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
                    contentsState = contentListState,
                    onClickItem = onClickContent,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Empty() {
    SearchResultScreen(
        searchResultScreenState = SearchResultScreenUiState.Empty,
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Loading() {
    SearchResultScreen(
        searchResultScreenState = SearchResultScreenUiState.Loading,
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Success() {
    Column {
        SearchResultScreen(
            searchResultScreenState = SearchResultScreenUiState.Success(
                attractionListState = ContentListUiState.Success(
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
                festivalListState = FestivalListUiState.Success(
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
                restaurantListState = ContentListUiState.Success(emptyList()),
                accommodationListState = ContentListUiState.Success(emptyList()),
            ),
            onClickContent = {},
        )
    }
}
