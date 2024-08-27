package com.dogeby.wheretogo.feature.contentdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.core.domain.tour.GetCommonInfoUseCase
import com.dogeby.wheretogo.core.ui.model.ReviewWithWriterListUiState
import com.dogeby.wheretogo.feature.contentdetail.model.ContentDetailScreenUiState
import com.dogeby.wheretogo.feature.contentdetail.model.RatingFilterOption
import com.dogeby.wheretogo.feature.contentdetail.navigation.ContentDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    stringDecoder: StringDecoder,
    savedStateHandle: SavedStateHandle,
    getCommonInfoUseCase: GetCommonInfoUseCase,
) : ViewModel() {

    private val contentDetailArgs = ContentDetailArgs(savedStateHandle, stringDecoder)

    val contentDetailScreenUiState = getCommonInfoUseCase(contentDetailArgs.contentId)
        .map { result ->
            val commonInfo = result.getOrNull()
            commonInfo?.let {
                ContentDetailScreenUiState.Success(
                    id = it.contentId,
                    title = it.title,
                    avgStarRating = 0.0,
                    modifiedTime = it.modifiedTime,
                    reviewWithWriterListUiState = ReviewWithWriterListUiState.Success(emptyList()),
                    imgSrcs = listOf(it.firstImageSrc ?: ""),
                    categories = listOf(
                        it.majorCategoryInfo?.name ?: "",
                        it.mediumCategoryInfo?.name ?: "",
                        it.minorCategoryInfo?.name ?: "",
                    ),
                    overview = it.overview ?: "",
                    tel = it.phoneNumber ?: "",
                    homepage = it.homepage ?: "",
                    address = if (it.addr2.isNullOrBlank()) {
                        it.addr1
                    } else {
                        "${it.addr1} ${it.addr2}"
                    } ?: "",
                    longitude = it.longitude?.toDoubleOrNull(),
                    latitude = it.latitude?.toDoubleOrNull(),
                    ratingFilterOption = RatingFilterOption.ALL,
                )
            } ?: ContentDetailScreenUiState.Loading
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ContentDetailScreenUiState.Loading,
        )
}
