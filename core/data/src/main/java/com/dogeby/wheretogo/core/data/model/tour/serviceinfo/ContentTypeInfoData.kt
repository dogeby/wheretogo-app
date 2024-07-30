package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

data class ContentTypeInfoData(
    val code: String,
    val name: String,
    val majorCategories: Map<String, MajorCategoryInfoData>,
)
