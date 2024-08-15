package com.dogeby.wheretogo.feature.contentdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.CategoryDisplay
import com.dogeby.wheretogo.core.ui.components.common.StarRatingDisplay
import com.dogeby.wheretogo.core.ui.util.formatDate
import sh.calvin.autolinktext.AutoLinkText

internal fun LazyListScope.commonContent(
    title: String,
    avgStarRating: Double,
    modifiedTime: String,
    categories: List<String> = emptyList(),
    overview: String = "",
    tel: String = "",
    homepage: String = "",
) {
    item {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                StarRatingDisplay(
                    avgStarRating = avgStarRating,
                    modifier = Modifier.padding(end = 8.dp),
                )
                CategoryDisplay(categories)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = overview,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (tel.isNotBlank()) {
                    Row {
                        Text(
                            text = "${stringResource(id = R.string.tel)}: ",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall,
                        )
                        AutoLinkText(
                            text = tel,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
                if (homepage.isNotBlank()) {
                    Row {
                        Text(
                            text = "${stringResource(id = R.string.homepage)}: ",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall,
                        )
                        AutoLinkText(
                            text = homepage,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
                val locale = LocalConfiguration.current.locales[0]
                Text(
                    text = "${stringResource(
                        id = R.string.modified_time,
                    )}: ${modifiedTime.formatDate(locale)}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonContentPreview() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
    ) {
        commonContent(
            title = "경복궁",
            avgStarRating = 4.5,
            modifiedTime = "20170825173054",
            categories = listOf("cat1", "cat2", "cat3"),
            overview = "경복궁은 1395년태조이성계에의해서새로운조선왕조의법궁으로지어졌다." +
                " 경복궁은동궐(창덕궁)이나서궐(경희궁)에비해위치가북쪽에있어 '북궐'이라불리기도했다.",
            tel = "02-999-9999",
            homepage = "http://www.royalpalace.go.kr",
        )
    }
}
