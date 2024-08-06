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
import com.dogeby.wheretogo.core.ui.components.common.CategoryDisplay
import com.dogeby.wheretogo.core.ui.components.common.LocationDisplay
import com.dogeby.wheretogo.core.ui.components.common.StarRatingDisplay

const val CONTENT_CARD_DEFAULT_ASPECT_RATIO = 1.6f

@Composable
fun ContentCard(
    title: String,
    imgSrc: Any,
    categories: List<String>,
    avgStarRating: Double,
    areaName: String,
    sigunguName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ratio: Float = CONTENT_CARD_DEFAULT_ASPECT_RATIO,
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
                CategoryDisplay(categories)
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
private fun ContentCardPreview() {
    ContentCard(
        title = "Title",
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg",
        categories = listOf("cat1", "cat2", "cat3"),
        avgStarRating = 4.5,
        areaName = "area",
        sigunguName = "sigungu",
        onClick = {},
        modifier = Modifier
            .padding(16.dp)
            .width(240.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentCardPreview_RatingZero() {
    ContentCard(
        title = "Title",
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg",
        categories = listOf("cat1", "cat2", "cat3"),
        avgStarRating = 0.0,
        areaName = "area",
        sigunguName = "sigungu",
        onClick = {},
        modifier = Modifier
            .padding(16.dp)
            .width(240.dp),
    )
}
