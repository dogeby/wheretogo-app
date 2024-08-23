package com.dogeby.wheretogo.feature.contents

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.core.domain.tour.GetContentTypeInfoMapUseCase
import com.dogeby.wheretogo.core.domain.tour.GetPagedTourContentUseCase
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState
import com.dogeby.wheretogo.core.ui.model.ContentListItemUiState
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsPageUiState
import com.dogeby.wheretogo.feature.contents.model.ContentsScreenUiState
import com.dogeby.wheretogo.feature.contents.model.MediumCategory
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

    private val mediumCategoryMap = contentTypeInfoMapState.map { contentTypeInfoMap ->
        contentTypeInfoMap?.flatMap { (_, contentTypeInfo) ->
            contentTypeInfo.majorCategories.values.flatMap { majorCategoryInfo ->
                majorCategoryInfo.mediumCategories.keys.map { mediumCategoryCode ->
                    mediumCategoryCode to
                        MediumCategory(mediumCategoryCode, majorCategoryInfo.categoryInfo.code)
                }
            }
        }?.toMap()
    }

    val contentsScreenState = combine(
        selectedContentType,
        selectedCategory,
        contentTypeInfoMapState,
    ) { selectedContentTypeId, selectedCategoryId, contentTypeInfoMap ->
        val destinations = TourContentType.getDestinations().map { it.id }
        val contentsPageStates = contentTypeInfoMap?.filter {
            it.key in destinations
        }?.map { (_, contentTypeInfo) ->
            val categories = contentTypeInfo.majorCategories.map { (_, majorCategoryInfo) ->
                majorCategoryInfo.mediumCategories.values.map { it.categoryInfo }
            }.flatten()

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
        if (contentsPageStates != null) {
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
    val contentsState =
        combine(
            selectedContentType,
            selectedCategory,
            mediumCategoryMap,
        ) { selectedContentTypeId, selectedCategoryId, mediumCategoryMap ->
            selectedContentTypeId to (
                mediumCategoryMap?.get(selectedCategoryId)
                    ?: MediumCategory("", "")
                )
        }.flatMapLatest { (selectedContentTypeId, mediumCategory) ->
            getPagedTourContentUseCase(
                contentTypeId = selectedContentTypeId,
                majorCategoryCode = mediumCategory.majorCategoryCode,
                mediumCategoryCode = mediumCategory.code,
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

    private companion object {

        const val CONTENTS_SELECTED_CONTENT_TYPE_ID_KEY = "contents_selected_content_type_id_key"
        const val CONTENTS_SELECTED_CATEGORY_ID_KEY = "contents_selected_category_id_key"
    }
}
