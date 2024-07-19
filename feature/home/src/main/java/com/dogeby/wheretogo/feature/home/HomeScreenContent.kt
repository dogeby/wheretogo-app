package com.dogeby.wheretogo.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Festival
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.carousel.ContentCardCarousel
import com.dogeby.wheretogo.core.ui.components.carousel.ContentCarousel
import com.dogeby.wheretogo.core.ui.components.carousel.FestivalCardCarousel
import com.dogeby.wheretogo.core.ui.components.common.ListTitleRow
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListUiState
import com.dogeby.wheretogo.core.ui.util.buildLocationContentTypeText
import com.dogeby.wheretogo.feature.home.model.HomeScreenUiState
import com.dogeby.wheretogo.feature.home.model.MenuIconBtnUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreenContent(
    homeScreenUiState: HomeScreenUiState.Success,
    onNavigateToList: (contentTypeId: String, areaCode: String, sigunguCode: String) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    with(homeScreenUiState) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                FestivalCardCarousel(
                    festivalsState = festivalPerformanceEventListState,
                    onClickItem = onNavigateToContentDetail,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    pageSize = PageSize.Fixed(360.dp),
                    pageSpacing = 16.dp,
                )
            }
            item {
                NavigationMenus(
                    menuIconBtnStates = menuIconBtnStates,
                    onNavigateToList = {
                        onNavigateToList(it, "", "")
                    },
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                )
            }
            contentsWithTitleRow(
                contentsState = touristSpotListState,
                onNavigateToContents = onNavigateToList,
                onNavigateToContentDetail = onNavigateToContentDetail,
            )
            contentCardsWithTitleRow(
                contentsState = culturalFacilityListState,
                onNavigateToList = onNavigateToList,
                onNavigateToContentDetail = onNavigateToContentDetail,
            )
            contentsWithTitleRow(
                contentsState = leisureSpotListState,
                onNavigateToContents = onNavigateToList,
                onNavigateToContentDetail = onNavigateToContentDetail,
            )
            contentCardsWithTitleRow(
                contentsState = restaurantListState,
                onNavigateToList = onNavigateToList,
                onNavigateToContentDetail = onNavigateToContentDetail,
            )
            contentsWithTitleRow(
                contentsState = accommodationListState,
                onNavigateToContents = onNavigateToList,
                onNavigateToContentDetail = onNavigateToContentDetail,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.contentCardsWithTitleRow(
    contentsState: ContentListUiState,
    onNavigateToList: (contentTypeId: String, areaCode: String, sigunguCode: String) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            item {
                with(contentsState) {
                    ListTitleRow(
                        title = buildLocationContentTypeText(
                            areaName = areaName,
                            sigunguName = sigunguName,
                            contentTypeName = contentTypeName,
                        ),
                        onClick = {
                            onNavigateToList(contentTypeId, areaCode, sigunguCode)
                        },
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                    ContentCardCarousel(
                        contentsState = this,
                        onClickItem = onNavigateToContentDetail,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        pageSize = PageSize.Fixed(240.dp),
                        pageSpacing = 16.dp,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.contentsWithTitleRow(
    contentsState: ContentListUiState,
    onNavigateToContents: (contentTypeId: String, areaCode: String, sigunguCode: String) -> Unit,
    onNavigateToContentDetail: (id: String) -> Unit,
) {
    when (contentsState) {
        ContentListUiState.Loading -> Unit
        is ContentListUiState.Success -> {
            item {
                with(contentsState) {
                    ListTitleRow(
                        title = buildLocationContentTypeText(
                            areaName = areaName,
                            sigunguName = sigunguName,
                            contentTypeName = contentTypeName,
                        ),
                        onClick = {
                            onNavigateToContents(contentTypeId, areaCode, sigunguCode)
                        },
                        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                    ContentCarousel(
                        contentsState = this,
                        onClickItem = onNavigateToContentDetail,
                        pageSize = PageSize.Fixed(360.dp),
                        verticalAlignment = Alignment.Top,
                        columns = 3,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    val festivalListUiState = FestivalListUiState.Success(
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
    )
    val contentListUiState = ContentListUiState.Success(
        contentTypeId = "12",
        contentTypeName = "관광지",
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
    )
    HomeScreenContent(
        homeScreenUiState = HomeScreenUiState.Success(
            menuIconBtnStates = List(4) {
                MenuIconBtnUiState(
                    contentTypeId = "",
                    icon = Icons.Outlined.Festival,
                    text = "Menu $it",
                )
            },
            festivalPerformanceEventListState = festivalListUiState,
            touristSpotListState = contentListUiState,
            culturalFacilityListState = contentListUiState,
            leisureSpotListState = contentListUiState,
            restaurantListState = contentListUiState,
            accommodationListState = contentListUiState,
        ),
        onNavigateToList = { _, _, _ -> },
        onNavigateToContentDetail = {},
    )
}
