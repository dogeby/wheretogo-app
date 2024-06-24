package com.dogeby.wheretogo.core.ui.components.listitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.IconText

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
        },
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
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
