package com.dogeby.wheretogo.feature.contentdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.map.TravelSpotMap

internal fun LazyListScope.mapContent(
    address: String,
    latitude: Double?,
    longitude: Double?,
) {
    item {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            ListItem(
                headlineContent = {
                    Text(
                        text = stringResource(id = R.string.address_and_map),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            )

            if (address.isNotBlank()) {
                Text(
                    text = address,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (latitude != null && longitude != null) {
                TravelSpotMap(
                    latitude = latitude,
                    longitude = longitude,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapContentPreview() {
    LazyColumn {
        mapContent(
            address = "address",
            latitude = 34.8043478048,
            longitude = 128.4207095494,
        )
    }
}
