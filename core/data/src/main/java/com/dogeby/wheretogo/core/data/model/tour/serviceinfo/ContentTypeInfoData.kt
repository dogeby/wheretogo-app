package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class ContentTypeInfoData(
    val code: String,
    val name: String,
    val majorCategories: Map<String, MajorCategoryInfoData>,
)

@Serializable
data class ContentTypeInfoMap(
    val contentTypeInfos: Map<String, ContentTypeInfoData>,
)
