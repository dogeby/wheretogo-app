package com.dogeby.wheretogo.core.domain.model.tour

import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchResultData
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.AreaInfo
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.LocationInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.CategoryInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ContentTypeInfo
import com.dogeby.wheretogo.core.domain.util.CategoryInfoUtil.getCategoryInfo
import com.dogeby.wheretogo.core.domain.util.CategoryInfoUtil.getContentTypeInfo
import com.dogeby.wheretogo.core.model.tour.TourContentType

data class KeywordSearchResult(
    val contentId: String,
    val contentType: TourContentType,
    val createdTime: String,
    val modifiedTime: String,
    val title: String,
    val addr1: String? = null,
    val addr2: String? = null,
    val areaInfo: LocationInfo? = null,
    val sigunguInfo: LocationInfo? = null,
    val majorCategoryInfo: CategoryInfo? = null,
    val mediumCategoryInfo: CategoryInfo? = null,
    val minorCategoryInfo: CategoryInfo? = null,
    val firstImageSrc: String? = null,
    val firstImageThumbnailSrc: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val mapLevel: String? = null,
    val phoneNumber: String? = null,
    val bookTour: String? = null,
    val copyrightType: String? = null,
)

/**
 * @throws NullPointerException if the content type info is not present in the map.
 */
internal fun KeywordSearchResultData.toKeywordSearchResult(
    contentTypeInfoMap: Map<String, ContentTypeInfo>,
    locationInfoMap: Map<String, AreaInfo>,
): KeywordSearchResult {
    val contentTypeInfo = getContentTypeInfo(
        contentTypeInfoMap = contentTypeInfoMap,
        contentTypeId = contentTypeId,
    )

    val (majorCatInfo, mediumCatInfo, minorCatInfo) = getCategoryInfo(
        majorCategories = contentTypeInfo.majorCategories,
        category1 = category1,
        category2 = category2,
        category3 = category3,
    )

    val areaInfo = locationInfoMap[areaCode]
    val sigunguInfo = areaInfo?.sigunguInfos?.get(sigunguCode)

    return KeywordSearchResult(
        contentId = contentId,
        contentType = contentTypeInfo.contentType,
        createdTime = createdTime,
        modifiedTime = modifiedTime,
        title = title,
        addr1 = addr1,
        addr2 = addr2,
        areaInfo = areaInfo?.locationInfo,
        sigunguInfo = sigunguInfo,
        majorCategoryInfo = majorCatInfo?.categoryInfo,
        mediumCategoryInfo = mediumCatInfo?.categoryInfo,
        minorCategoryInfo = minorCatInfo,
        firstImageSrc = firstImageSrc,
        firstImageThumbnailSrc = firstImageThumbnailSrc,
        longitude = longitude,
        latitude = latitude,
        mapLevel = mapLevel,
        phoneNumber = phoneNumber,
        bookTour = bookTour,
        copyrightType = copyrightType,
    )
}
