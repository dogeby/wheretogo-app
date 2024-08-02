package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData

data class MediumCategoryInfo(
    val code: String,
    val name: String,
    val minorCategories: Map<String, MinorCategoryInfo>,
)

internal fun MediumCategoryInfoData.toMediumCategoryInfo() = MediumCategoryInfo(
    code = code,
    name = name,
    minorCategories = minorCategories.mapValues {
        it.value.toMinorCategoryInfo()
    },
)
