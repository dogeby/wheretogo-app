package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.card.ContentCard
import com.dogeby.wheretogo.core.ui.components.listitem.ContentListItem
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState

fun LazyGridScope.contentList(
    contentsState: ContentListUiState,
    onClickItem: (id: String) -> Unit,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            items(contentsState.contents) { content ->
                with(content) {
                    ContentListItem(
                        title = title,
                        imgSrc = imgSrc,
                        categories = categories,
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

fun LazyListScope.contentCardList(
    contentsState: ContentListUiState,
    onClickItem: (id: String) -> Unit,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            items(contentsState.contents) { content ->
                with(content) {
                    ContentCard(
                        title = title,
                        imgSrc = imgSrc,
                        categories = categories,
                        avgStarRating = avgStarRating,
                        areaName = areaName,
                        sigunguName = sigunguName,
                        onClick = {
                            onClickItem(id)
                        },
                        modifier = Modifier.width(240.dp),
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        contentList(
            contentsState = ContentListUiState.Success(
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
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentCardListPreview_LazyListScope() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        contentCardList(
            contentsState = ContentListUiState.Success(
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
            onClickItem = {},
        )
    }
}
