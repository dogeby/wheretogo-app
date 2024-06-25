package com.dogeby.wheretogo.core.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.dogeby.wheretogo.core.ui.components.common.IconText

@Composable
fun ContentMediumCard(
    title: String,
    imgSrc: String,
    categories: List<String>,
    avgStarRating: Double,
    areaName: String,
    sigunguName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    color: Color = Color.Transparent,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.width(240.dp),
        shape = shape,
        color = color,
    ) {
        Column {
            AsyncImageWithFallback(
                imgSrc = imgSrc,
                modifier = Modifier
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = categories.joinToString(" · "),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    IconText(
                        icon = Icons.Default.StarRate,
                        text = String.format(
                            locale = null,
                            format = "%.1f",
                            avgStarRating.coerceIn(0.0, 5.0),
                        ),
                    )
                    IconText(
                        icon = Icons.Default.LocationOn,
                        text = "$areaName $sigunguName",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentMediumCardPreview() {
    ContentMediumCard(
        title = "Title",
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image3_1.jpg",
        categories = listOf("cat1", "cat2", "cat3"),
        avgStarRating = 4.5,
        areaName = "area",
        sigunguName = "sigungu",
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}
