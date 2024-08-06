package com.dogeby.wheretogo.feature.home.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BeachAccess
import androidx.compose.material.icons.outlined.Festival
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.R

enum class ContentTypeMenu(
    val contentType: TourContentType,
    val icon: ImageVector,
    @StringRes val nameResId: Int,
) {
    Destination(
        contentType = TourContentType.TouristSpot,
        icon = Icons.Outlined.BeachAccess,
        nameResId = R.string.destination,
    ),
    Festival(
        contentType = TourContentType.Festival,
        icon = Icons.Outlined.Festival,
        nameResId = R.string.festival,
    ),
    Accommodation(
        contentType = TourContentType.Accommodation,
        icon = Icons.Outlined.Hotel,
        nameResId = R.string.accommodation,
    ),
    Restaurant(
        contentType = TourContentType.Restaurant,
        icon = Icons.Outlined.Restaurant,
        nameResId = R.string.restaurant,
    ),
}
