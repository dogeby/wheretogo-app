package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.CategoryInfoData

data class CategoryInfo(
    val code: String,
    val name: String,
)

internal fun CategoryInfoData.toCategoryInfo() = CategoryInfo(
    code = code,
    name = name,
)
