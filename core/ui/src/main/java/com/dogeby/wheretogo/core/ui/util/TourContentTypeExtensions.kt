package com.dogeby.wheretogo.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.ui.R

@Composable
fun TourContentType.getDisplayName(): String {
    return when (this) {
        TourContentType.TouristSpot -> stringResource(R.string.tourist_spot)
        TourContentType.CulturalFacility -> stringResource(R.string.cultural_facility)
        TourContentType.Festival -> stringResource(R.string.festival)
        TourContentType.LeisureSport -> stringResource(R.string.leisure_sport)
        TourContentType.Shopping -> stringResource(R.string.shopping)
        TourContentType.TravelCourse -> stringResource(R.string.travel_course)
        TourContentType.Accommodation -> stringResource(R.string.accommodation)
        TourContentType.Restaurant -> stringResource(R.string.restaurant)
    }
}
