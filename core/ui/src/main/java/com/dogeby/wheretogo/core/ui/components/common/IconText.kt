package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val ICON_TEXT_DEFAULT_ICON_SIZE = 12.dp

@Composable
fun IconText(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    contentDescription: String? = null,
    iconSize: Dp = ICON_TEXT_DEFAULT_ICON_SIZE,
    style: TextStyle = MaterialTheme.typography.labelMedium,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = color,
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = text,
            color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = style,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IconTextPreview() {
    IconText(
        icon = Icons.Default.StarRate,
        text = "5.0",
        modifier = Modifier.padding(16.dp),
    )
}
