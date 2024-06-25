package com.dogeby.wheretogo.core.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import com.dogeby.wheretogo.core.ui.components.common.IconText
import com.dogeby.wheretogo.core.ui.components.common.StarRatingDisplay
import com.dogeby.wheretogo.core.ui.components.util.formatDate

@Composable
fun FestivalCard(
    title: String,
    imgSrc: String,
    startDate: String,
    endDate: String,
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
        modifier = modifier,
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

                val locale = LocalConfiguration.current.locales[0]
                IconText(
                    icon = Icons.Default.CalendarToday,
                    text = "${startDate.formatDate(locale)} - ${endDate.formatDate(locale)}",
                    style = MaterialTheme.typography.bodyMedium,
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
        modifier = Modifier.width(360.dp).padding(16.dp),
    )
}
