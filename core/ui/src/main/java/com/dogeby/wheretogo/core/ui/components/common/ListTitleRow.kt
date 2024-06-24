package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListTitleRow(
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowForward,
    contentDescription: String? = null,
) {
    ListItem(
        headlineContent = {
            Text(text = title)
        },
        modifier = if (onClick != null) {
            modifier.clickable { onClick() }
        } else {
            modifier
        },
        trailingContent = {
            onClick?.let {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = contentDescription,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ListTitleRowPreview_OnClickIsNull() {
    ListTitleRow(
        title = "Title",
        modifier = Modifier.padding(16.dp),
        onClick = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun ListTitleRowPreview() {
    ListTitleRow(
        title = "Title",
        modifier = Modifier.padding(16.dp),
        onClick = {},
    )
}
