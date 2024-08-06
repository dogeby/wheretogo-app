package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
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
import com.dogeby.wheretogo.core.ui.components.card.CONTENT_CARD_DEFAULT_ASPECT_RATIO
import com.dogeby.wheretogo.core.ui.components.card.ContentCard
import com.dogeby.wheretogo.core.ui.components.listitem.ContentListItem
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import kotlinx.coroutines.flow.flowOf

fun LazyGridScope.contentList(
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickItem: (id: String) -> Unit,
) {
    if (contents.loadState.refresh is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
    items(contents.itemCount) { index ->
        contents[index]?.let {
            ContentListItem(
                title = it.title,
                imgSrc = it.imgSrc,
                categories = it.categories,
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
    if (contents.loadState.append is LoadState.Loading) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}

fun LazyListScope.contentCardList(
    contents: LazyPagingItems<ContentListItemUiState>,
    onClickItem: (id: String) -> Unit,
) {
    val contentCardWidth = 240.dp
    if (contents.loadState.refresh is LoadState.Loading) {
        item {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(contentCardWidth)
                    .aspectRatio(CONTENT_CARD_DEFAULT_ASPECT_RATIO)
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
    items(contents.itemCount) { index ->
        contents[index]?.let {
            ContentCard(
                title = it.title,
                imgSrc = it.imgSrc,
                categories = it.categories,
                avgStarRating = it.avgStarRating,
                areaName = it.areaName,
                sigunguName = it.sigunguName,
                onClick = {
                    onClickItem(it.id)
                },
                modifier = Modifier.width(240.dp),
            )
        }
    }
    if (contents.loadState.append is LoadState.Loading) {
        item {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(contentCardWidth)
                    .aspectRatio(CONTENT_CARD_DEFAULT_ASPECT_RATIO)
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentListPreview() {
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        contentList(
            contents = pagedContents,
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentListPreview_Loading() {
    val contents = List(3) {
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
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.Loading,
            ),
        ),
    ).collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        contentList(
            contents = pagedContents,
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentCardListPreview_LazyListScope() {
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

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        contentCardList(
            contents = pagedContents,
            onClickItem = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentCardListPreview_LazyListScope_Loading() {
    val contents = List(3) {
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
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.Loading,
            ),
        ),
    ).collectAsLazyPagingItems()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        contentCardList(
            contents = pagedContents,
            onClickItem = {},
        )
    }
}
