package com.dogeby.wheretogo.core.data.model.tour.locationinfo

import kotlinx.serialization.Serializable

@Serializable
data class AreaInfoData(
    val locationInfo: LocationInfoData,
    val sigunguInfos: Map<String, LocationInfoData>,
)

@Serializable
data class AreaInfoMap(
    val areaInfos: Map<String, AreaInfoData>,
)
