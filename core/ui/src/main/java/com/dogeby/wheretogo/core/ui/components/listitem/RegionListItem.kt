package com.dogeby.wheretogo.core.ui.components.listitem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback

@Composable
fun RegionListItem(
    imgSrc: Any,
    name: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
) {
    ListItem(
        headlineContent = {
            Text(
                text = name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        modifier = modifier,
        leadingContent = {
            AsyncImageWithFallback(
                imgSrc = imgSrc,
                modifier = Modifier.size(48.dp),
                contentDescription = contentDescription,
            )
        },
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    )
}

@Preview(showBackground = true)
@Composable
private fun RegionListItemPreview() {
    RegionListItem(
        imgSrc = "",
        name = "Region",
    )
}
