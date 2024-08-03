package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData

data class ContentTypeInfo(
    val serviceInfo: ServiceInfo,
    val majorCategories: Map<String, MajorCategoryInfo>,
)

internal fun ContentTypeInfoData.toContentTypeInfo() = ContentTypeInfo(
    serviceInfo = serviceInfoData.toServiceInfo(),
    majorCategories = majorCategories.mapValues {
        it.value.toMajorCategoryInfo()
    },
)
