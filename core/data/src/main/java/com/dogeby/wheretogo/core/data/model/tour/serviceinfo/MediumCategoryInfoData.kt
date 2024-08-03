package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class MediumCategoryInfoData(
    val serviceInfoData: ServiceInfoData,
    val minorCategories: Map<String, ServiceInfoData>,
)
