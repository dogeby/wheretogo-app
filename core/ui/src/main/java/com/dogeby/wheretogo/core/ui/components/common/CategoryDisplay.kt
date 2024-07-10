package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategoryDisplay(
    categories: List<String>,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelMedium,
) {
    Text(
        text = categories.joinToString(" • "),
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = style,
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryDisplayPreview() {
    CategoryDisplay(categories = listOf("cat1", "cat2", "cat3"))
}
