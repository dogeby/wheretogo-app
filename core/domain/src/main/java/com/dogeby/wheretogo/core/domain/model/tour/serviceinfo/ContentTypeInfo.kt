package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData

data class ContentTypeInfo(
    val code: String,
    val name: String,
    val majorCategories: Map<String, MajorCategoryInfo>,
)

internal fun ContentTypeInfoData.toContentTypeInfo() = ContentTypeInfo(
    code = code,
    name = name,
    majorCategories = majorCategories.mapValues {
        it.value.toMajorCategoryInfo()
    },
)
