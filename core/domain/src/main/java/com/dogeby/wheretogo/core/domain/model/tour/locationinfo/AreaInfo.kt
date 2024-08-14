package com.dogeby.wheretogo.core.domain.model.tour.locationinfo

import com.dogeby.wheretogo.core.data.model.tour.locationinfo.AreaInfoData

data class AreaInfo(
    val locationInfo: LocationInfo,
    val sigunguInfos: Map<String, LocationInfo>,
)

internal fun AreaInfoData.toAreaInfo() = AreaInfo(
    locationInfo = locationInfo.toLocationInfo(),
    sigunguInfos = sigunguInfos.mapValues {
        it.value.toLocationInfo()
    },
)
