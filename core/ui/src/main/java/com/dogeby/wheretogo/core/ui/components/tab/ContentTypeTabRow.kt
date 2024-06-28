package com.dogeby.wheretogo.core.ui.components.tab

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTypeTabRow(
    tabStates: List<ContentTypeTabUiState>,
    onClickTab: (index: Int, id: String) -> Unit,
    modifier: Modifier = Modifier,
    state: MutableIntState = remember(tabStates) { mutableIntStateOf(0) },
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
) {
    PrimaryTabRow(
        selectedTabIndex = state.intValue,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        tabStates.forEachIndexed { index, (id, name) ->
            Tab(
                selected = state.intValue == index,
                onClick = {
                    state.intValue = index
                    onClickTab(index, id)
                },
                text = {
                    Text(
                        text = name,
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentTypeTabRowPreview() {
    ContentTypeTabRow(
        tabStates = List(4) {
            ContentTypeTabUiState(
                id = "$it",
                name = "name $it",
            )
        },
        onClickTab = { _, _ -> },
    )
}
