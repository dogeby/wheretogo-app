package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

data class MediumCategoryInfoData(
    val code: String,
    val name: String,
    val minorCategories: Map<String, MinorCategoryInfoData>,
)
