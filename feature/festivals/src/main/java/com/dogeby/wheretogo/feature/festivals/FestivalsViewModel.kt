package com.dogeby.wheretogo.feature.festivals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.CategoryHierarchy
import com.dogeby.wheretogo.core.domain.tour.GetContentTypeInfoMapUseCase
import com.dogeby.wheretogo.core.domain.tour.GetPagedFestivalUseCase
import com.dogeby.wheretogo.core.domain.tour.category.GetMediumCategoryHierarchyUseCase
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.FestivalListItemUiState
import com.dogeby.wheretogo.feature.festivals.model.FestivalsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FestivalsViewModel @Inject constructor(
    getPagedFestivalUseCase: GetPagedFestivalUseCase,
    getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
    getMediumCategoryHierarchyUseCase: GetMediumCategoryHierarchyUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val selectedMediumCategory: StateFlow<String?> = savedStateHandle
        .getStateFlow(
            key = FESTIVALS_SELECTED_CATEGORY_CODE_KEY,
            initialValue = null,
        )

    val festivalsScreenState = combine(
        selectedMediumCategory,
        getContentTypeInfoMapUseCase(),
    ) { selectedMediumCategoryCode, contentTypeInfoMapResult ->
        val contentTypeInfo = contentTypeInfoMapResult
            .getOrNull()
            ?.get(TourContentType.Festival.id)

        if (contentTypeInfo != null) {
            val categories = contentTypeInfo.majorCategories.values.map { majorCategoryInfo ->
                majorCategoryInfo.mediumCategories.values.map { it.categoryInfo }
            }.flatten()

            FestivalsScreenUiState.Success(
                categoryChipStates = categories.map { (code, name) ->
                    CategoryChipUiState(
                        id = code,
                        name = name,
                        isSelected = code == selectedMediumCategoryCode,
                    )
                },
            )
        } else {
            FestivalsScreenUiState.Loading
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FestivalsScreenUiState.Loading,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val festivalsState = combine(
        selectedMediumCategory,
        getMediumCategoryHierarchyUseCase(),
    ) { selectedMediumCategoryCode, mediumCategoryMap ->
        selectedMediumCategoryCode?.let {
            mediumCategoryMap?.get(it)
        } ?: CategoryHierarchy()
    }.flatMapLatest { filterCategoryHierarchy ->
        getPagedFestivalUseCase(filterCategoryHierarchy = filterCategoryHierarchy)
            .map { pagingData ->
                pagingData.map {
                    FestivalListItemUiState(
                        id = it.contentId,
                        title = it.title,
                        startDate = it.eventStartDate,
                        endDate = it.eventEndDate,
                        imgSrc = it.firstImageThumbnailSrc ?: "",
                        areaName = it.areaInfo?.name ?: "",
                        sigunguName = it.sigunguInfo?.name ?: "",
                    )
                }
            }
    }.cachedIn(viewModelScope)

    fun setCategoryCode(categoryCode: String?) {
        savedStateHandle.set(
            key = FESTIVALS_SELECTED_CATEGORY_CODE_KEY,
            value = categoryCode,
        )
    }

    private companion object {

        const val FESTIVALS_SELECTED_CATEGORY_CODE_KEY = "festivals_selected_category_code_key"
    }
}
