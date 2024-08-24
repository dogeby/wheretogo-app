package com.dogeby.wheretogo.feature.regionselection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.core.domain.tour.areainfo.GetAreaInfoMapUseCase
import com.dogeby.wheretogo.core.ui.model.RegionListItemUiState
import com.dogeby.wheretogo.core.ui.model.RegionListUiState
import com.dogeby.wheretogo.feature.regionselection.navigation.RegionSelectionArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RegionSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    getAreaInfoMapUseCase: GetAreaInfoMapUseCase,
) : ViewModel() {

    private val regionSelectionArgs = RegionSelectionArgs(savedStateHandle, stringDecoder)
    val contentTypeId = regionSelectionArgs.contentTypeId

    val regionListState = getAreaInfoMapUseCase()
        .map { result ->
            result.getOrNull()?.let { areaInfoMap ->
                RegionListUiState.Success(
                    regions = areaInfoMap.values.map {
                        RegionListItemUiState(
                            code = it.locationInfo.code,
                            name = it.locationInfo.name,
                        )
                    },
                )
            } ?: RegionListUiState.Loading
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RegionListUiState.Loading,
        )
}
