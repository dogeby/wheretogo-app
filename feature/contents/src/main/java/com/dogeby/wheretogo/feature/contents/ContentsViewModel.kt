package com.dogeby.wheretogo.feature.contents

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.CategoryHierarchy
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.CategoryInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ContentTypeInfo
import com.dogeby.wheretogo.core.domain.tour.GetContentTypeInfoMapUseCase
import com.dogeby.wheretogo.core.domain.tour.GetPagedTourContentUseCase
import com.dogeby.wheretogo.core.domain.tour.category.GetMediumCategoryHierarchyUseCase
import com.dogeby.wheretogo.core.domain.tour.category.GetMinorCategoryHierarchyUseCase
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsPageUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsScreenUiState
import com.dogeby.wheretogo.feature.contents.navigation.ContentsArgs
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
class ContentsViewModel @Inject constructor(
    stringDecoder: StringDecoder,
    getPagedTourContentUseCase: GetPagedTourContentUseCase,
    getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
    getMinorCategoryHierarchyUseCase: GetMinorCategoryHierarchyUseCase,
    getMediumCategoryHierarchyUseCase: GetMediumCategoryHierarchyUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val contentsArgs = ContentsArgs(savedStateHandle, stringDecoder)

    private val selectedContentType = savedStateHandle
        .getStateFlow(
            key = CONTENTS_SELECTED_CONTENT_TYPE_ID_KEY,
            initialValue = contentsArgs.contentTypeId,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = contentsArgs.contentTypeId,
        )
    private val selectedCategory: StateFlow<String?> = savedStateHandle
        .getStateFlow(
            key = CONTENTS_SELECTED_CATEGORY_ID_KEY,
            initialValue = null,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    private val contentTypeInfoMapState = getContentTypeInfoMapUseCase().map { result ->
        result.getOrNull()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val mediumCategoryMap = getMediumCategoryHierarchyUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    private val minorCategoryMap = getMinorCategoryHierarchyUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    val contentsScreenState = combine(
        selectedContentType,
        selectedCategory,
        contentTypeInfoMapState,
    ) { selectedContentTypeId, selectedCategoryId, contentTypeInfoMap ->
        if (contentTypeInfoMap != null) {
            val contentsPageStates = getScreenContentTypeInfoList(
                contentTypeInfoMap,
            ).map { contentTypeInfo ->
                val categories = getScreenCategoryInfoList(contentTypeInfo)

                ContentsPageUiState(
                    contentTypeTabState = ContentTypeTabUiState(
                        contentType = contentTypeInfo.contentType,
                        isSelected = contentTypeInfo.contentType.id == selectedContentTypeId,
                    ),
                    categoryChipStates = categories.map { (code, name) ->
                        CategoryChipUiState(
                            id = code,
                            name = name,
                            isSelected = code == selectedCategoryId,
                        )
                    },
                )
            }
            ContentsScreenUiState.Success(contentsPageStates)
        } else {
            ContentsScreenUiState.Loading
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ContentsScreenUiState.Loading,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val contentsState = combine(
        selectedContentType,
        selectedCategory,
        if (isScreenContentTypeDestination()) mediumCategoryMap else minorCategoryMap,
    ) { selectedContentTypeId, selectedCategoryId, categoryMap ->
        selectedContentTypeId to (
            categoryMap?.get(selectedCategoryId)
                ?: CategoryHierarchy("", "")
            )
    }.flatMapLatest { (selectedContentTypeId, category) ->
        getPagedTourContentUseCase(
            contentTypeId = selectedContentTypeId,
            majorCategoryCode = category.majorCategoryCode,
            mediumCategoryCode = category.mediumCategoryCode,
            minorCategoryCode = category.minorCategoryCode,
            areaCode = contentsArgs.areaCode,
        ).map { pagingData ->
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
    }

    fun setContentTypeId(id: String) {
        savedStateHandle.set(
            key = CONTENTS_SELECTED_CONTENT_TYPE_ID_KEY,
            value = id,
        )
        setCategoryId(categoryId = null)
    }

    fun setCategoryId(categoryId: String?) {
        savedStateHandle.set(
            key = CONTENTS_SELECTED_CATEGORY_ID_KEY,
            value = categoryId,
        )
    }

    private fun isScreenContentTypeDestination(): Boolean {
        return when (contentsArgs.contentTypeId) {
            TourContentType.Accommodation.id,
            TourContentType.Restaurant.id,
            -> false
            else -> true
        }
    }

    private fun getScreenContentTypeInfoList(
        contentTypeInfoMap: Map<String, ContentTypeInfo>,
    ): List<ContentTypeInfo> {
        val contentTypes = when (contentsArgs.contentTypeId) {
            TourContentType.Accommodation.id -> {
                listOf(TourContentType.Accommodation)
            }
            TourContentType.Restaurant.id -> {
                listOf(TourContentType.Restaurant)
            }
            else -> {
                TourContentType.getDestinations()
            }
        }
        return contentTypeInfoMap.values.filter { it.contentType in contentTypes }
    }

    private fun getScreenCategoryInfoList(contentTypeInfo: ContentTypeInfo): List<CategoryInfo> {
        return if (isScreenContentTypeDestination()) {
            contentTypeInfo.majorCategories.map { (_, majorCategoryInfo) ->
                majorCategoryInfo.mediumCategories.values.map { it.categoryInfo }
            }.flatten()
        } else {
            contentTypeInfo.majorCategories.values.map { majorCategoryInfo ->
                majorCategoryInfo.mediumCategories.values.map { mediumCategoryInfo ->
                    mediumCategoryInfo.minorCategories.values
                }.flatten()
            }.flatten()
        }
    }

    private companion object {

        const val CONTENTS_SELECTED_CONTENT_TYPE_ID_KEY = "contents_selected_content_type_id_key"
        const val CONTENTS_SELECTED_CATEGORY_ID_KEY = "contents_selected_category_id_key"
    }
}
