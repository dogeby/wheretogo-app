package com.dogeby.wheretogo.feature.festivals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.chip.CategoryChipRow
import com.dogeby.wheretogo.core.ui.components.common.EmptyListDisplay
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.list.festivalList
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState
import com.dogeby.wheretogo.feature.festivals.model.FestivalsScreenUiState

@Composable
internal fun FestivalsScreen(
    festivalsScreenState: FestivalsScreenUiState,
    onClickCategoryChip: (categoryId: String) -> Unit,
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
                        when (festivalsState) {
                            FestivalListUiState.Loading -> LoadingDisplay()
                            is FestivalListUiState.Success -> {
                                if (festivalsState.festivals.isEmpty()) {
                                    EmptyListDisplay()
                                } else {
                                    LazyVerticalGrid(
                                        columns = GridCells.Adaptive(360.dp),
                                        contentPadding = PaddingValues(bottom = 16.dp),
                                    ) {
                                        festivalList(
                                            festivalsState = festivalsState,
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
private fun FestivalsScreenPreview() {
    FestivalsScreen(
        festivalsScreenState = FestivalsScreenUiState.Success(
            categoryChipStates = List(5) {
                CategoryChipUiState(
                    id = it.toString(),
                    name = "name $it",
                )
            },
            festivalsState = FestivalListUiState.Success(
                contentTypeId = "15",
                contentTypeName = "축제/공연/행사",
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
        ),
        onClickCategoryChip = {},
        onClickContent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun FestivalsScreenPreview_Empty() {
    FestivalsScreen(
        festivalsScreenState = FestivalsScreenUiState.Success(
            categoryChipStates = List(5) {
                CategoryChipUiState(
                    id = it.toString(),
                    name = "name $it",
                )
            },
            festivalsState = FestivalListUiState.Success(
                contentTypeId = "15",
                contentTypeName = "축제/공연/행사",
                festivals = emptyList(),
            ),
        ),
        onClickCategoryChip = {},
        onClickContent = {},
    )
}
