package com.dogeby.wheretogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.wheretogo.core.domain.tour.FetchContentTypeInfoMapUseCase
import com.dogeby.wheretogo.core.domain.tour.areainfo.FetchAreaInfoMapUseCase
import com.dogeby.wheretogo.model.MainActivityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    fetchAreaInfoMapUseCase: FetchAreaInfoMapUseCase,
    fetchContentTypeInfoMapUseCase: FetchContentTypeInfoMapUseCase,
) : ViewModel() {

    val uiState = fetchContentTypeInfoMapUseCase()
        .combine(
            fetchAreaInfoMapUseCase(),
        ) { contentTypeInfoResult: Result<Unit>, areaInfoResult: Result<Unit> ->
            if (contentTypeInfoResult.isSuccess && areaInfoResult.isSuccess) {
                MainActivityUiState.Success
            } else {
                MainActivityUiState.Failure
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}
