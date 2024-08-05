package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData

data class MajorCategoryInfo(
    val categoryInfo: CategoryInfo,
    val mediumCategories: Map<String, MediumCategoryInfo>,
)

internal fun MajorCategoryInfoData.toMajorCategoryInfo() = MajorCategoryInfo(
    categoryInfo = categoryInfoData.toCategoryInfo(),
    mediumCategories = mediumCategories.mapValues {
        it.value.toMediumCategoryInfo()
    },
)
