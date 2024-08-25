package com.dogeby.wheretogo.feature.festivals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.components.chip.CategoryChipRow
import com.dogeby.wheretogo.core.ui.components.common.EmptyListDisplay
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.list.festivalList
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.feature.festivals.model.FestivalsScreenUiState
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun FestivalsRoute(
    navigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FestivalsViewModel = hiltViewModel(),
) {
    val festivalsScreenState by viewModel.festivalsScreenState.collectAsStateWithLifecycle()
    val festivals = viewModel.festivalsState.collectAsLazyPagingItems()

    FestivalsScreen(
        festivalsScreenState = festivalsScreenState,
        festivals = festivals,
        onClickCategoryChip = viewModel::setCategoryCode,
        onClickContent = navigateToContentDetail,
        modifier = modifier,
    )
}

@Composable
internal fun FestivalsScreen(
    festivalsScreenState: FestivalsScreenUiState,
    festivals: LazyPagingItems<FestivalListItemUiState>,
    onClickCategoryChip: (categoryId: String?) -> Unit,
    onClickContent: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (festivalsScreenState) {
        FestivalsScreenUiState.Loading -> LoadingDisplay(modifier = modifier)
        is FestivalsScreenUiState.Success -> {
            Column(modifier = modifier) {
                with(festivalsScreenState) {
                    Column {
                        CategoryChipRow(
                            chipStates = categoryChipStates,
                            onClickChip = {
                                onClickCategoryChip(it)
                            },
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        )
                        when {
                            festivals.loadState.refresh is LoadState.Loading -> {
                                LoadingDisplay()
                            }
                            festivals.itemCount == 0 &&
                                festivals.loadState.append !is LoadState.Loading -> {
                                EmptyListDisplay()
                            }
                            else -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(360.dp),
                                    contentPadding = PaddingValues(bottom = 16.dp),
                                ) {
                                    festivalList(
                                        festivals = festivals,
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
private fun FestivalsScreenPreview() {
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

    FestivalsScreen(
        festivalsScreenState = FestivalsScreenUiState.Success(
            categoryChipStates = List(5) {
                CategoryChipUiState(
                    id = it.toString(),
                    name = "name $it",
                )
            },
        ),
        festivals = pagedFestivals,
        onClickCategoryChip = {},
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun FestivalsScreenPreview_Empty() {
    val pagedFestivals = flowOf(
        PagingData.empty<FestivalListItemUiState>(),
    ).collectAsLazyPagingItems()

    FestivalsScreen(
        festivalsScreenState = FestivalsScreenUiState.Success(
            categoryChipStates = List(5) {
                CategoryChipUiState(
                    id = it.toString(),
                    name = "name $it",
                )
            },
        ),
        festivals = pagedFestivals,
        onClickCategoryChip = {},
        onClickContent = {},
    )
}
