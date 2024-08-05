package com.dogeby.wheretogo.core.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import com.dogeby.wheretogo.core.ui.components.common.DateRangeDisplay
import com.dogeby.wheretogo.core.ui.components.common.LocationDisplay
import com.dogeby.wheretogo.core.ui.components.common.StarRatingDisplay

const val FESTIVAL_CARD_DEFAULT_ASPECT_RATIO = 1.6f

@Composable
fun FestivalCard(
    title: String,
    imgSrc: Any,
    startDate: String,
    endDate: String,
    avgStarRating: Double,
    areaName: String,
    sigunguName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ratio: Float = FESTIVAL_CARD_DEFAULT_ASPECT_RATIO,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        Column {
            AsyncImageWithFallback(
                imgSrc = imgSrc,
                modifier = Modifier
                    .aspectRatio(ratio)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                )
                DateRangeDisplay(
                    startDate = startDate,
                    endDate = endDate,
                    style = MaterialTheme.typography.bodyMedium,
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FestivalCardPreview() {
    FestivalCard(
        title = "Title",
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/54/2483454_image2_1.JPG",
        startDate = "20210306",
        endDate = "20211030",
        avgStarRating = 4.5,
        areaName = "area",
        sigunguName = "sigungu",
        onClick = {},
        modifier = Modifier
            .width(360.dp)
            .padding(16.dp),
    )
}
