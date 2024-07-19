package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.card.FestivalCard
import com.dogeby.wheretogo.core.ui.components.listitem.FestivalListItem
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

fun LazyGridScope.festivalCardList(
    festivalsState: FestivalListUiState,
    onClickItem: (id: String) -> Unit,
) {
    when (festivalsState) {
        FestivalListUiState.Loading -> Unit
        is FestivalListUiState.Success -> {
            items(festivalsState.festivals) { festival ->
                with(festival) {
                    FestivalCard(
                        title = title,
                        imgSrc = imgSrc,
                        startDate = startDate,
                        endDate = endDate,
                        avgStarRating = avgStarRating,
                        areaName = areaName,
                        sigunguName = sigunguName,
                        onClick = {
                            onClickItem(id)
                        },
                    )
                }
            }
        }
    }
}

fun LazyGridScope.festivalList(
    festivalsState: FestivalListUiState,
    onClickItem: (id: String) -> Unit,
) {
    when (festivalsState) {
        FestivalListUiState.Loading -> Unit
        is FestivalListUiState.Success -> {
            items(festivalsState.festivals) { festival ->
                with(festival) {
                    FestivalListItem(
                        title = title,
                        imgSrc = imgSrc,
                        startDate = startDate,
                        endDate = endDate,
                        avgStarRating = avgStarRating,
                        areaName = areaName,
                        sigunguName = sigunguName,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                        ) {
                            onClickItem(id)
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalCardListPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        festivalCardList(
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
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalListPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        festivalList(
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
            onClickItem = {},
        )
    }
}
