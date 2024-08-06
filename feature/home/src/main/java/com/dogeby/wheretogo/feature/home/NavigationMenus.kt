package com.dogeby.wheretogo.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.components.button.MenuIconButton
import com.dogeby.wheretogo.feature.home.model.ContentTypeMenu

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationMenus(
    onNavigateToList: (contentType: TourContentType) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        ContentTypeMenu.entries.forEach {
            MenuIconButton(
                icon = it.icon,
                text = stringResource(id = it.nameResId),
                onClick = { onNavigateToList(it.contentType) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationMenusPreview() {
    NavigationMenus(
        onNavigateToList = {},
        modifier = Modifier.fillMaxWidth(),
    )
}
