package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData

data class MediumCategoryInfo(
    val categoryInfo: CategoryInfo,
    val minorCategories: Map<String, CategoryInfo>,
)

internal fun MediumCategoryInfoData.toMediumCategoryInfo() = MediumCategoryInfo(
    categoryInfo = categoryInfoData.toCategoryInfo(),
    minorCategories = minorCategories.mapValues {
        it.value.toCategoryInfo()
    },
)
