package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class MajorCategoryInfoData(
    val serviceInfoData: ServiceInfoData,
    val mediumCategories: Map<String, MediumCategoryInfoData>,
)
