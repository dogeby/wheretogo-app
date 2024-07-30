package com.dogeby.wheretogo.core.data.model.tour

import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoData

data class LocationInfoData(
    val rnum: Int,
    val code: String,
    val name: String,
)

internal fun NetworkLocationInfoData.toLocationInfoData() = LocationInfoData(
    rnum = rnum,
    code = code,
    name = name,
)
