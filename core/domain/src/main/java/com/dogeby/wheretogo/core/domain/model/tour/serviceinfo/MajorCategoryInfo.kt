package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData

data class MajorCategoryInfo(
    val code: String,
    val name: String,
    val mediumCategories: Map<String, MediumCategoryInfo>,
)

internal fun MajorCategoryInfoData.toMajorCategoryInfo() = MajorCategoryInfo(
    code = code,
    name = name,
    mediumCategories = mediumCategories.mapValues {
        it.value.toMediumCategoryInfo()
    },
)
