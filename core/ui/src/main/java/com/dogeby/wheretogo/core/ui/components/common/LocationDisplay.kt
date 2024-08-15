package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.util.buildLocationText

@Composable
fun LocationDisplay(
    modifier: Modifier = Modifier,
    areaName: String = "",
    sigunguName: String = "",
) {
    val locationText = buildLocationText(areaName, sigunguName)

    if (locationText.isBlank()) {
        Text(
            text = "",
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium,
        )
    } else {
        IconText(
            icon = Icons.Default.LocationOn,
            text = locationText,
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationDisplayPreview() {
    LocationDisplay(
        areaName = "area",
        sigunguName = "sigungi",
    )
}

@Preview(showBackground = true)
@Composable
private fun LocationDisplayPreview_Single() {
    LocationDisplay(
        areaName = "area",
    )
}

@Preview(showBackground = true)
@Composable
private fun LocationDisplayPreview_BothEmpty() {
    LocationDisplay()
}
