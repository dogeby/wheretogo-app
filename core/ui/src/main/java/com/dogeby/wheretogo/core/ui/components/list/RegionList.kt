package com.dogeby.wheretogo.core.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.listitem.RegionListItem
import com.dogeby.wheretogo.core.ui.model.RegionListItemUiState
import com.dogeby.wheretogo.core.ui.model.RegionListUiState

fun LazyGridScope.regionList(
    regionsState: RegionListUiState,
    onClickItem: (code: String) -> Unit,
) {
    when (regionsState) {
        RegionListUiState.Loading -> Unit
        is RegionListUiState.Success -> {
            items(regionsState.regions) { region ->
                with(region) {
                    RegionListItem(
                        imgSrc = imgSrc,
                        name = name,
                        modifier = Modifier.clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = rememberRipple(),
                        ) {
                            onClickItem(code)
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegionListPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(360.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        regionList(
            regionsState = RegionListUiState.Success(
                regions = List(10) {
                    RegionListItemUiState(
                        code = it.toString(),
                        name = it.toString(),
                    )
                },
            ),
            onClickItem = {},
        )
    }
}
