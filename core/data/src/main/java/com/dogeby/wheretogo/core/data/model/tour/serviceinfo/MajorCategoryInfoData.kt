package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class MajorCategoryInfoData(
    val categoryInfoData: CategoryInfoData,
    val mediumCategories: Map<String, MediumCategoryInfoData>,
)
