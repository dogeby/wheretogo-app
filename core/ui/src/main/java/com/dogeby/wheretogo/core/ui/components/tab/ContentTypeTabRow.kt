package com.dogeby.wheretogo.core.ui.components.tab

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.model.ContentTypeTabUiState
import com.dogeby.wheretogo.core.ui.util.getDisplayName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTypeTabRow(
    tabStates: List<ContentTypeTabUiState>,
    onClickTab: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
) {
    PrimaryScrollableTabRow(
        selectedTabIndex = tabStates.indexOfFirst { it.isSelected },
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        tabStates.forEach { (contentType, isSelected) ->
            Tab(
                selected = isSelected,
                onClick = {
                    onClickTab(contentType.id)
                },
                text = {
                    Text(contentType.getDisplayName())
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentTypeTabRowPreview() {
    ContentTypeTabRow(
        tabStates = TourContentType
            .getDestinations()
            .mapIndexed { index, tourContentType ->
                ContentTypeTabUiState(tourContentType, index == 0)
            },
        onClickTab = {},
    )
}
