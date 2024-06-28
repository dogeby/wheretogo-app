package com.dogeby.wheretogo.core.ui.components.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.model.CategoryChipUiState

@Composable
fun CategoryChipRow(
    chipStates: List<CategoryChipUiState>,
    onClickChip: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(chipStates) { (id, name) ->
            var selected by rememberSaveable(
                key = id,
            ) {
                mutableStateOf(false)
            }

            FilterChip(
                selected = selected,
                onClick = {
                    selected = !selected
                    onClickChip(id)
                },
                label = {
                    Text(text = name)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipRowPreview() {
    CategoryChipRow(
        chipStates = List(5) {
            CategoryChipUiState(
                id = it.toString(),
                name = "name $it",
            )
        },
        onClickChip = {},
        contentPadding = PaddingValues(horizontal = 16.dp),
    )
}
