package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dogeby.wheretogo.core.ui.components.card.FestivalCard
import com.dogeby.wheretogo.core.ui.components.listitem.FestivalListItem
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import kotlinx.coroutines.flow.flowOf

fun LazyGridScope.festivalCardList(
    festivals: LazyPagingItems<FestivalListItemUiState>,
    onClickItem: (id: String) -> Unit,
) {
    if (festivals.loadState.refresh is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
    items(festivals.itemCount) { index ->
        festivals[index]?.let {
            FestivalCard(
                title = it.title,
                imgSrc = it.imgSrc,
                startDate = it.startDate,
                endDate = it.endDate,
                avgStarRating = it.avgStarRating,
                areaName = it.areaName,
                sigunguName = it.sigunguName,
                onClick = {
                    onClickItem(it.id)
                },
            )
        }
    }
    if (festivals.loadState.append is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}

fun LazyGridScope.festivalList(
    festivals: LazyPagingItems<FestivalListItemUiState>,
    onClickItem: (id: String) -> Unit,
) {
    if (festivals.loadState.refresh is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
    items(festivals.itemCount) { index ->
        festivals[index]?.let {
            FestivalListItem(
                title = it.title,
                imgSrc = it.imgSrc,
                startDate = it.startDate,
                endDate = it.endDate,
                avgStarRating = it.avgStarRating,
                areaName = it.areaName,
                sigunguName = it.sigunguName,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                ) {
                    onClickItem(it.id)
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            )
        }
    }
    if (festivals.loadState.append is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalCardListPreview() {
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        festivalCardList(
            festivals = pagedFestivals,
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalCardListPreview_Loading() {
    val festivals = List(3) {
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
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.Loading,
            ),
        ),
    ).collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        festivalCardList(
            festivals = pagedFestivals,
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalListPreview() {
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        festivalList(
            festivals = pagedFestivals,
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalListPreview_Loading() {
    val festivals = List(3) {
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
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.Loading,
            ),
        ),
    ).collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        festivalList(
            festivals = pagedFestivals,
            onClickItem = {},
        )
    }
}
