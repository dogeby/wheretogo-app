package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.card.FestivalCard
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState

fun LazyGridScope.festivalList(
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

@Preview(showBackground = true)
@Composable
private fun ContentListPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        festivalList(
            festivalsState = FestivalListUiState.Success(
                festivals = List(10) {
                    FestivalListItemUiState(
                        id = "$it",
                        title = "Title",
                        startDate = "20210306",
                        endDate = "20211030",
                        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/" +
                            "2678623_image3_1.jpg",
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
