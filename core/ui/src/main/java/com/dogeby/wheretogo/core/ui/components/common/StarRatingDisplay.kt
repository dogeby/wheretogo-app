package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StarRatingDisplay(
    avgStarRating: Double,
    modifier: Modifier = Modifier,
    minRating: Double = 1.0,
    maxRating: Double = 5.0,
) {
    if (avgStarRating in minRating..maxRating) {
        IconText(
            icon = Icons.Default.StarRate,
            text = String.format(
                locale = null,
                format = "%.1f",
                avgStarRating,
            ),
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StarRatingDisplayPreview() {
    StarRatingDisplay(avgStarRating = 3.5)
}
