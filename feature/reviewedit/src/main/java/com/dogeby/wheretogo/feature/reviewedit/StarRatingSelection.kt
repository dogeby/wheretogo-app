package com.dogeby.wheretogo.feature.reviewedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun StarRatingSelection(
    starRating: () -> Int,
    onStarRatingChanged: (starRating: Int) -> Unit,
    modifier: Modifier = Modifier,
    maxStarRating: Int = 5,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
    ) {
        repeat(maxStarRating) { index ->
            IconButton(
                onClick = { onStarRatingChanged(index + 1) },
                modifier = Modifier.size(56.dp),
            ) {
                Icon(
                    imageVector = if (index < starRating()) {
                        Icons.Filled.StarRate
                    } else {
                        Icons.Outlined.StarRate
                    },
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StarRatingSelectionPreview() {
    var starRating by remember {
        mutableIntStateOf(0)
    }
    StarRatingSelection(
        starRating = { starRating },
        onStarRatingChanged = { starRating = it },
        modifier = Modifier.width(360.dp),
    )
}
