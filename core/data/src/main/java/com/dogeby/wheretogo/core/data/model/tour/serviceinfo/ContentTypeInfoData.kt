package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import com.dogeby.wheretogo.core.model.tour.TourContentType
import kotlinx.serialization.Serializable

@Serializable
data class ContentTypeInfoData(
    val contentType: TourContentType,
    val majorCategories: Map<String, MajorCategoryInfoData>,
)

@Serializable
data class ContentTypeInfoMap(
    val contentTypeInfos: Map<String, ContentTypeInfoData>,
)
