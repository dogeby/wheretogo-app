package com.dogeby.wheretogo.core.ui.components.tab

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTypeTabRow(
    tabStates: List<ContentTypeTabUiState>,
    onClickTab: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember {
        mutableIntStateOf(0)
    }
    PrimaryScrollableTabRow(
        selectedTabIndex = state,
        modifier = modifier,
    ) {
        tabStates.forEachIndexed { index, (id, name) ->
            Tab(
                selected = state == index,
                onClick = {
                    state = index
                    onClickTab(id)
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
        tabStates = List(6) {
            ContentTypeTabUiState(
                id = "$it",
                name = "name $it",
            )
        },
        onClickTab = {},
    )
}
