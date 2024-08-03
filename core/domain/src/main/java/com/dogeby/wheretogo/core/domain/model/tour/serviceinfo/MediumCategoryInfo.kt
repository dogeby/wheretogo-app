package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData

data class MediumCategoryInfo(
    val serviceInfo: ServiceInfo,
    val minorCategories: Map<String, ServiceInfo>,
)

internal fun MediumCategoryInfoData.toMediumCategoryInfo() = MediumCategoryInfo(
    serviceInfo = serviceInfoData.toServiceInfo(),
    minorCategories = minorCategories.mapValues {
        it.value.toServiceInfo()
    },
)
