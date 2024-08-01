package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class MediumCategoryInfoData(
    val code: String,
    val name: String,
    val minorCategories: Map<String, MinorCategoryInfoData>,
)
