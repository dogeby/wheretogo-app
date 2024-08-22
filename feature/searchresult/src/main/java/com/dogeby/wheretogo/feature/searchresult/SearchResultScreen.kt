package com.dogeby.wheretogo.feature.searchresult

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.components.common.ErrorDisplay
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.common.NoSearchResultsDisplay
import com.dogeby.wheretogo.core.ui.components.list.contentList
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun SearchResultRoute(
    navigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val contents = viewModel.searchResults.collectAsLazyPagingItems()

    SearchResultScreen(
        contents = contents,
        onClickContent = navigateToContentDetail,
        modifier = modifier,
    )
}

@Composable
internal fun SearchResultScreen(
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        contents.loadState.refresh is LoadState.Loading -> {
            LoadingDisplay()
        }
        contents.itemCount == 0 -> {
            NoSearchResultsDisplay(modifier = modifier)
        }
        contents.loadState.hasError -> {
            ErrorDisplay()
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(360.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                contentList(
                    contents = contents,
                    onClickItem = onClickContent,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Empty() {
    val pagedContents = flowOf(
        PagingData.empty<ContentListItemUiState>(),
    ).collectAsLazyPagingItems()

    SearchResultScreen(
        contents = pagedContents,
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchResultScreenPreview_Success() {
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
            contents = pagedContents,
            onClickContent = {},
        )
    }
}
