package com.dogeby.wheretogo.core.domain.model.tour

data class AreaInfo(
    val locationInfo: LocationInfo,
    val sigunguInfos: Map<String, LocationInfo>,
)
