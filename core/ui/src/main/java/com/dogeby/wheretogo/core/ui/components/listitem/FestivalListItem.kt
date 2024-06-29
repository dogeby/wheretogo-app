package com.dogeby.wheretogo.core.ui.components.listitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import com.dogeby.wheretogo.core.ui.components.common.DateRangeDisplay
import com.dogeby.wheretogo.core.ui.components.common.LocationDisplay
import com.dogeby.wheretogo.core.ui.components.common.StarRatingDisplay

@Composable
fun FestivalListItem(
    title: String,
    imgSrc: Any,
    startDate: String,
    endDate: String,
    avgStarRating: Double,
    areaName: String,
    sigunguName: String,
    modifier: Modifier = Modifier,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        modifier = modifier,
        supportingContent = {
            Column {
                DateRangeDisplay(
                    startDate = startDate,
                    endDate = endDate,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    StarRatingDisplay(avgStarRating)
                    LocationDisplay(
                        areaName = areaName,
                        sigunguName = sigunguName,
                    )
                }
            }
        },
        leadingContent = {
            AsyncImageWithFallback(
                imgSrc = imgSrc,
                modifier = Modifier.size(56.dp),
            )
        },
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    )
}

@Preview(showBackground = true)
@Composable
private fun FestivalListItemPreview() {
    FestivalListItem(
        title = "Title",
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/54/2483454_image2_1.JPG",
        startDate = "20210306",
        endDate = "20211030",
        avgStarRating = 4.5,
        areaName = "area",
        sigunguName = "sigungu",
    )
}

@Preview(showBackground = true)
@Composable
private fun FestivalListItemPreview_OptionParameterIsEmpty() {
    FestivalListItem(
        title = "Title",
        imgSrc = "",
        startDate = "20210306",
        endDate = "20211030",
        avgStarRating = 0.0,
        areaName = "",
        sigunguName = "",
    )
}
