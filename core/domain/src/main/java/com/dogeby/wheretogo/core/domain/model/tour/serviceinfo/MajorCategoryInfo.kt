package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData

data class MajorCategoryInfo(
    val serviceInfo: ServiceInfo,
    val mediumCategories: Map<String, MediumCategoryInfo>,
)

internal fun MajorCategoryInfoData.toMajorCategoryInfo() = MajorCategoryInfo(
    serviceInfo = serviceInfoData.toServiceInfo(),
    mediumCategories = mediumCategories.mapValues {
        it.value.toMediumCategoryInfo()
    },
)
