package com.dogeby.wheretogo.core.ui.components.listitem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun KeywordListItem(
    icon: ImageVector,
    keyword: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    ListItem(
        headlineContent = {
            Text(
                text = keyword,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        modifier = modifier,
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun KeywordListItemPreview() {
    KeywordListItem(
        icon = Icons.Default.History,
        keyword = "keyword",
    )
}
