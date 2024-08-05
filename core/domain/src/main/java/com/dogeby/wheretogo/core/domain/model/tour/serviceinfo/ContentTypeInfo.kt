package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.model.tour.TourContentType

data class ContentTypeInfo(
    val contentType: TourContentType,
    val majorCategories: Map<String, MajorCategoryInfo>,
)

internal fun ContentTypeInfoData.toContentTypeInfo() = ContentTypeInfo(
    contentType = contentType,
    majorCategories = majorCategories.mapValues {
        it.value.toMajorCategoryInfo()
    },
)
