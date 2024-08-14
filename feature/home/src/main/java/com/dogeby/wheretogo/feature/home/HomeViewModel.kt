package com.dogeby.wheretogo.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dogeby.wheretogo.core.domain.tour.GetPagedFestivalUseCase
import com.dogeby.wheretogo.core.domain.tour.GetPagedTourContentUseCase
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentListUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.feature.home.model.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPagedFestivalUseCase: GetPagedFestivalUseCase,
    getPagedTourContentUseCase: GetPagedTourContentUseCase,
) : ViewModel() {

    val festivalPerformanceEventListState = getPagedFestivalUseCase()
        .map { pagingData ->
            pagingData.map {
                FestivalListItemUiState(
                    id = it.contentId,
                    title = it.title,
                    startDate = it.eventStartDate,
                    endDate = it.eventEndDate,
                    imgSrc = it.firstImageSrc ?: "",
                    areaName = it.areaInfo?.name ?: "",
                    sigunguName = it.sigunguInfo?.name ?: "",
                )
            }
        }
        .cachedIn(viewModelScope)

    val homeScreenUiState = HomeScreenUiState.Success(
        contentListStates = TourContentType.getAllExceptFestival().map {
            ContentListUiState.Success(
                contentType = it,
            )
        },
    )

    val contentListStates = homeScreenUiState.contentListStates.map { contentListUiState ->
        when (contentListUiState) {
            ContentListUiState.Loading -> flowOf(
                PagingData.from<ContentListItemUiState>(
                    data = emptyList(),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.Loading,
                        prepend = LoadState.NotLoading(true),
                        append = LoadState.NotLoading(false),
                    ),
                ),
            )
            is ContentListUiState.Success -> {
                getPagedTourContentUseCase(
                    contentTypeId = contentListUiState.contentType.id,
                    areaCode = contentListUiState.areaCode,
                    sigunguCode = contentListUiState.sigunguCode,
                )
                    .map { pagingData ->
                        pagingData.map {
                            ContentListItemUiState(
                                id = it.contentId,
                                title = it.title,
                                imgSrc = it.firstImageSrc ?: "",
                                categories = listOf(
                                    it.majorCategoryInfo?.name ?: "",
                                    it.mediumCategoryInfo?.name ?: "",
                                    it.minorCategoryInfo?.name ?: "",
                                ),
                                areaName = it.areaInfo?.name ?: "",
                                sigunguName = it.sigunguInfo?.name ?: "",
                            )
                        }
                    }
                    .cachedIn(viewModelScope)
            }
        }
    }
}
