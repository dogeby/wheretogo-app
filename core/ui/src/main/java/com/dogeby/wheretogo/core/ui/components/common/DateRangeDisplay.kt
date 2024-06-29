package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.util.formatDate
import java.util.Locale

@Composable
fun DateRangeDisplay(
    startDate: String,
    endDate: String,
    modifier: Modifier = Modifier,
    locale: Locale = LocalConfiguration.current.locales[0],
    style: TextStyle = MaterialTheme.typography.labelMedium,
) {
    IconText(
        icon = Icons.Default.CalendarToday,
        text = "${startDate.formatDate(locale)} - ${endDate.formatDate(locale)}",
        modifier = modifier,
        style = style,
    )
}

@Preview(showBackground = true)
@Composable
fun DateRangeDisplayPreview() {
    DateRangeDisplay(
        startDate = "20210306",
        endDate = "20211030",
    )
}
