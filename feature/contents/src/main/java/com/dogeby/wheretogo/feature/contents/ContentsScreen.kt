package com.dogeby.wheretogo.feature.contents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.chip.CategoryChipRow
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.list.contentList
import com.dogeby.wheretogo.core.ui.components.tab.ContentTypeTabRow
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsPageUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsScreenUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContentsScreen(
    contentsScreenState: ContentsScreenUiState,
    onClickContentTypeTab: (id: String) -> Unit,
    onClickCategoryChip: (contentTypeId: String, categoryId: String) -> Unit,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    when (contentsScreenState) {
        ContentsScreenUiState.Loading -> LoadingDisplay(modifier = modifier)
        is ContentsScreenUiState.Success -> {
            val selectedTabIndex = rememberSaveable(contentsScreenState.pageStates.size) {
                mutableIntStateOf(0)
            }
            Column(modifier = modifier) {
                val pagerState = rememberPagerState {
                    contentsScreenState.pageStates.size
                }

                ContentTypeTabRow(
                    tabStates = contentsScreenState.pageStates.map { it.contentTypeTabState },
                    onClickTab = { index, id ->
                        selectedTabIndex.intValue = index
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                        onClickContentTypeTab(id)
                    },
                    state = selectedTabIndex,
                    containerColor = Color.Transparent,
                )
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                ) {
                    with(contentsScreenState.pageStates[selectedTabIndex.intValue]) {
                        if (contentsState is ContentListUiState.Loading) {
                            LoadingDisplay()
                        } else {
                            Column {
                                CategoryChipRow(
                                    chipStates = categoryChipStates,
                                    onClickChip = {
                                        onClickCategoryChip(contentTypeTabState.id, it)
                                    },
                                    state = LazyListState(),
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                )
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(360.dp),
                                    contentPadding = PaddingValues(bottom = 16.dp),
                                ) {
                                    contentList(
                                        contentsState = contentsState,
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

@Preview(showBackground = true)
@Composable
private fun ContentScreenPreview() {
    val tabAndCategory = remember {
        mutableMapOf(
            "0" to "00",
            "1" to "12",
        )
    }
    val pageStates = List(4) { tabIndex ->
        ContentsPageUiState(
            ContentTypeTabUiState(
                id = tabIndex.toString(),
                name = "name $tabIndex",
            ),
            categoryChipStates = List(5) {
                CategoryChipUiState(
                    id = "$tabIndex$it",
                    name = "name $tabIndex$it",
                    isSelected = tabAndCategory["$tabIndex"] == "$tabIndex$it",
                )
            },
            contentsState = ContentListUiState.Success(
                contents = List(20) {
                    ContentListItemUiState(
                        id = "$tabIndex$it",
                        title = "Title $tabIndex $it",
                        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/" +
                            "2678623_image3_1.jpg",
                        categories = listOf("cat1", "cat2", "cat3"),
                        avgStarRating = 4.5,
                        areaName = "area",
                        sigunguName = "sigungu",
                    )
                },
            ),
        )
    }
    ContentsScreen(
        contentsScreenState = ContentsScreenUiState.Success(pageStates),
        onClickContentTypeTab = {},
        onClickCategoryChip = { _, _ -> },
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentScreenPreview_Loading() {
    ContentsScreen(
        contentsScreenState = ContentsScreenUiState.Loading,
        onClickContentTypeTab = {},
        onClickCategoryChip = { _, _ -> },
        onClickContent = {},
    )
}
