package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

data class MajorCategoryInfoData(
    val code: String,
    val name: String,
    val mediumCategories: Map<String, MediumCategoryInfoData>,
)
