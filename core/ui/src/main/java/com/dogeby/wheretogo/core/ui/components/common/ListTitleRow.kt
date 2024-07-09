package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ListTitleRow(
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    contentDescription: String? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        modifier = if (onClick != null) {
            modifier.clickable { onClick() }
        } else {
            modifier
        },
        trailingContent = {
            if (trailingIcon != null && onClick != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = contentDescription,
                )
            }
        },
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    )
}

@Preview(showBackground = true)
@Composable
private fun ListTitleRowPreview_OnClickIsNull() {
    ListTitleRow(
        title = "Title",
        modifier = Modifier.padding(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun ListTitleRowPreview() {
    ListTitleRow(
        title = "Title",
        modifier = Modifier.padding(16.dp),
        onClick = {},
        trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
    )
}
