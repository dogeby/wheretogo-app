package com.dogeby.wheretogo.feature.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.core.domain.tour.SearchKeywordUseCase
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.feature.searchresult.navigation.SearchResultArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    searchKeywordUseCase: SearchKeywordUseCase,
) : ViewModel() {

    private val searchResultArgs = SearchResultArgs(savedStateHandle, stringDecoder)

    val searchResults = searchKeywordUseCase(searchResultArgs.searchKeyword)
        .map { pagingData ->
            pagingData.map {
                ContentListItemUiState(
                    id = it.contentId,
                    title = it.title,
                    imgSrc = it.firstImageThumbnailSrc ?: "",
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
