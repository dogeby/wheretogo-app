package com.dogeby.wheretogo.core.ui.components.listitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import com.dogeby.wheretogo.core.ui.components.common.IconText
import com.dogeby.wheretogo.core.ui.components.common.StarRatingDisplay

@Composable
fun ContentListItem(
    title: String,
    imgSrc: String,
    categories: List<String>,
    avgStarRating: Double,
    areaName: String,
    sigunguName: String,
    modifier: Modifier = Modifier,
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
                Text(
                    text = categories.joinToString(" • "),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    StarRatingDisplay(avgStarRating)
                    IconText(
                        icon = Icons.Default.LocationOn,
                        text = "$areaName $sigunguName",
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
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentListItemPreview() {
    ContentListItem(
        title = "Title",
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg",
        categories = listOf("cat1", "cat2", "cat3"),
        avgStarRating = 4.5,
        areaName = "area",
        sigunguName = "sigungu",
    )
}
