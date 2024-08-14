package com.dogeby.wheretogo.core.domain.model.tour.locationinfo

import com.dogeby.wheretogo.core.data.model.tour.locationinfo.LocationInfoData

data class LocationInfo(
    val code: String,
    val name: String,
)

internal fun LocationInfoData.toLocationInfo() = LocationInfo(
    code = code,
    name = name,
)
