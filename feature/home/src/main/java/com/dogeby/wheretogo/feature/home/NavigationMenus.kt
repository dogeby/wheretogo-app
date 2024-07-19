package com.dogeby.wheretogo.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Festival
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.components.button.MenuIconButton
import com.dogeby.wheretogo.feature.home.model.MenuIconBtnUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationMenus(
    menuIconBtnStates: List<MenuIconBtnUiState>,
    onNavigateToList: (contentTypeId: String) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        menuIconBtnStates.forEach {
            MenuIconButton(
                icon = it.icon,
                text = it.text,
                onClick = { onNavigateToList(it.contentTypeId) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationMenusPreview() {
    NavigationMenus(
        menuIconBtnStates = List(5) {
            MenuIconBtnUiState(
                contentTypeId = "",
                icon = Icons.Outlined.Festival,
                text = "Festival $it",
            )
        },
        onNavigateToList = {},
        modifier = Modifier.fillMaxWidth(),
    )
}
