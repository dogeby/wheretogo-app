package com.dogeby.wheretogo.feature.regionselection

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.LoadingDisplay
import com.dogeby.wheretogo.core.ui.components.list.regionList
import com.dogeby.wheretogo.core.ui.model.RegionListItemUiState
import com.dogeby.wheretogo.core.ui.model.RegionListUiState

@Composable
internal fun RegionSelectionScreen(
    regionsState: RegionListUiState,
    onNavigateToList: (areaCode: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (regionsState) {
        RegionListUiState.Loading -> LoadingDisplay(modifier = modifier)
        is RegionListUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(360.dp),
                modifier = modifier,
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                regionList(
                    regionsState = regionsState,
                    onClickItem = onNavigateToList,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegionSelectionScreenPreview() {
    RegionSelectionScreen(
        regionsState = RegionListUiState.Success(
            regions = List(10) {
                RegionListItemUiState(
                    code = it.toString(),
                    name = it.toString(),
                )
            },
        ),
        onNavigateToList = {},
    )
}
