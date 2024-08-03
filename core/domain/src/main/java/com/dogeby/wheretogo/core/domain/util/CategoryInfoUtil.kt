package com.dogeby.wheretogo.core.domain.util

import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ContentTypeInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.MajorCategoryInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.MediumCategoryInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ServiceInfo

internal object CategoryInfoUtil {

    /**
     * @throws NullPointerException if the content type info is not present in the map.
     */
    fun getContentTypeInfo(
        contentTypeInfoMap: Map<String, ContentTypeInfo>,
        contentTypeId: String?,
    ): ContentTypeInfo {
        return contentTypeInfoMap[contentTypeId]
            ?: throw NullPointerException("Content type info not found for \"$contentTypeId\"")
    }

    fun getCategoryInfo(
        majorCategories: Map<String, MajorCategoryInfo>?,
        category1: String?,
        category2: String? = null,
        category3: String? = null,
    ): Triple<MajorCategoryInfo?, MediumCategoryInfo?, ServiceInfo?> {
        val majorCategoryInfo = majorCategories?.get(category1)
        val mediumCategoryInfo = majorCategoryInfo?.mediumCategories?.get(category2)
        val minorCategoryInfo = mediumCategoryInfo?.minorCategories?.get(category3)
        return Triple(
            majorCategoryInfo,
            mediumCategoryInfo,
            minorCategoryInfo,
        )
    }
}
