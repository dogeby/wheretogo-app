package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class MediumCategoryInfoData(
    val categoryInfoData: CategoryInfoData,
    val minorCategories: Map<String, CategoryInfoData>,
)
