package com.dogeby.wheretogo.core.ui.components.listitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback

@Composable
fun ProfileListItem(
    imgSrc: Any,
    nickname: String,
    modifier: Modifier = Modifier,
    totalReviewCount: Int = 0,
    avgStarRating: Double = 0.0,
) {
    Row(
        modifier = modifier.padding(
            start = 16.dp,
            top = 12.dp,
            end = 4.dp,
            bottom = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImageWithFallback(
            imgSrc = imgSrc,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = nickname,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${stringResource(
                        id = R.string.review,
                    )} $totalReviewCount • ${stringResource(id = R.string.avg_star_rating)} ${
                        String.format(
                            locale = null,
                            format = "%.1f",
                            avgStarRating,
                        )
                    }",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileListItemPreview() {
    ProfileListItem(
        imgSrc = "",
        nickname = "nickname",
        totalReviewCount = 5,
        avgStarRating = 4.5,
    )
}
