package com.dogeby.wheretogo.feature.contents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.components.chip.CategoryChipRow
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.common.NoSearchResultsDisplay
import com.dogeby.wheretogo.core.ui.components.list.contentList
import com.dogeby.wheretogo.core.ui.components.tab.ContentTypeTabRow
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsPageUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsScreenUiState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
internal fun ContentsRoute(
    navigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContentsViewModel = hiltViewModel(),
) {
    val contentsScreenState by viewModel.contentsScreenState.collectAsStateWithLifecycle()
    val contents = viewModel.contentsState.collectAsLazyPagingItems()

    ContentsScreen(
        contentsScreenState = contentsScreenState,
        contents = contents,
        onClickContentTypeTab = viewModel::setContentTypeId,
        onClickCategoryChip = viewModel::setCategoryId,
        onClickContent = navigateToContentDetail,
        modifier = modifier,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContentsScreen(
    contentsScreenState: ContentsScreenUiState,
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickContentTypeTab: (id: String) -> Unit,
    onClickCategoryChip: (categoryId: String?) -> Unit,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val contentsState = rememberLazyGridState()

    when (contentsScreenState) {
        ContentsScreenUiState.Loading -> LoadingDisplay(modifier = modifier)
        is ContentsScreenUiState.Success -> {
            Column(modifier = modifier) {
                val pagerState = rememberPagerState {
                    contentsScreenState.pageStates.size
                }

                ContentTypeTabRow(
                    tabStates = contentsScreenState.pageStates.map { it.contentTypeTabState },
                    onClickTab = { id ->
                        coroutineScope.launch {
                            pagerState.scrollToPage(
                                contentsScreenState
                                    .pageStates
                                    .indexOfFirst {
                                        it.contentTypeTabState.contentType.id == id
                                    },
                            )
                            contentsState.scrollToItem(0)
                        }
                        onClickContentTypeTab(id)
                    },
                    containerColor = Color.Transparent,
                )
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                ) {
                    val contentsPageState = contentsScreenState.pageStates.firstOrNull {
                        it.contentTypeTabState.isSelected
                    }
                    if (contentsPageState != null) {
                        Column {
                            CategoryChipRow(
                                chipStates = contentsPageState.categoryChipStates,
                                onClickChip = {
                                    onClickCategoryChip(it)
                                },
                                contentPadding = PaddingValues(horizontal = 16.dp),
                            )
                            when {
                                contents.loadState.refresh is LoadState.Loading -> {
                                    LoadingDisplay()
                                }
                                contents.itemCount == 0 -> {
                                    NoSearchResultsDisplay(modifier = modifier)
                                }
                                else -> {
                                    LazyVerticalGrid(
                                        columns = GridCells.Adaptive(360.dp),
                                        contentPadding = PaddingValues(bottom = 16.dp),
                                        state = contentsState,
                                    ) {
                                        contentList(
                                            contents = contents,
                                            onClickItem = onClickContent,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentScreenPreview() {
    val tabAndCategory = remember {
        mutableMapOf(
            "0" to "00",
            "1" to "12",
        )
    }
    val pageStates = TourContentType
        .getDestinations()
        .mapIndexed { tabIndex, tourContentType ->
            ContentsPageUiState(
                contentTypeTabState = ContentTypeTabUiState(
                    contentType = tourContentType,
                    isSelected = tabIndex == 0,
                ),
                categoryChipStates = List(5) {
                    CategoryChipUiState(
                        id = "$tabIndex$it",
                        name = "name $tabIndex$it",
                        isSelected = tabAndCategory["$tabIndex"] == "$tabIndex$it",
                    )
                },
            )
        }
    val contents = List(20) {
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

    ContentsScreen(
        contentsScreenState = ContentsScreenUiState.Success(pageStates),
        contents = pagedContents,
        onClickContentTypeTab = {},
        onClickCategoryChip = {},
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentScreenPreview_Loading() {
    val pagedContents = flowOf(
        PagingData.empty<ContentListItemUiState>(),
    ).collectAsLazyPagingItems()

    ContentsScreen(
        contentsScreenState = ContentsScreenUiState.Loading,
        contents = pagedContents,
        onClickContentTypeTab = {},
        onClickCategoryChip = {},
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentScreenPreview_Empty() {
    val tabAndCategory = remember {
        mutableMapOf(
            "0" to "00",
            "1" to "12",
        )
    }
    val pageStates = TourContentType
        .getDestinations()
        .mapIndexed { tabIndex, tourContentType ->
            ContentsPageUiState(
                contentTypeTabState = ContentTypeTabUiState(
                    contentType = tourContentType,
                    isSelected = tabIndex == 0,
                ),
                categoryChipStates = List(5) {
                    CategoryChipUiState(
                        id = "$tabIndex$it",
                        name = "name $tabIndex$it",
                        isSelected = tabAndCategory["$tabIndex"] == "$tabIndex$it",
                    )
                },
            )
        }
    val pagedContents = flowOf(
        PagingData.empty<ContentListItemUiState>(),
    ).collectAsLazyPagingItems()

    ContentsScreen(
        contentsScreenState = ContentsScreenUiState.Success(pageStates),
        contents = pagedContents,
        onClickContentTypeTab = {},
        onClickCategoryChip = {},
        onClickContent = {},
    )
}
