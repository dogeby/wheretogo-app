package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.util.buildLocationText

@Composable
fun LocationDisplay(
    modifier: Modifier = Modifier,
    areaName: String = "",
    sigunguName: String = "",
) {
    if (areaName.isBlank() && sigunguName.isBlank()) {
        return
    }
    val locationText = buildLocationText(areaName, sigunguName)

    IconText(
        icon = Icons.Default.LocationOn,
        text = locationText,
        modifier = modifier,
    )
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
