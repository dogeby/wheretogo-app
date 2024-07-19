package com.dogeby.wheretogo.feature.home.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BeachAccess
import androidx.compose.material.icons.outlined.Festival
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import com.dogeby.wheretogo.core.ui.R

enum class ContentTypeMenu(
    val contentTypeId: String,
    val icon: ImageVector,
    @StringRes val nameResId: Int,
) {
    Destination(
        contentTypeId = "12",
        icon = Icons.Outlined.BeachAccess,
        nameResId = R.string.destination,
    ),
    Festival(
        contentTypeId = "15",
        icon = Icons.Outlined.Festival,
        nameResId = R.string.festival,
    ),
    Accommodation(
        contentTypeId = "32",
        icon = Icons.Outlined.Hotel,
        nameResId = R.string.accommodation,
    ),
    Restaurant(
        contentTypeId = "39",
        icon = Icons.Outlined.Restaurant,
        nameResId = R.string.restaurant,
    ),
}
