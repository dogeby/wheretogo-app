package com.dogeby.wheretogo.core.domain.model.tour.tourcontent

import com.dogeby.wheretogo.core.data.model.tour.TourContentData
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.AreaInfo
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.LocationInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ContentTypeInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ServiceInfo
import com.dogeby.wheretogo.core.domain.util.CategoryInfoUtil.getCategoryInfo
import com.dogeby.wheretogo.core.domain.util.CategoryInfoUtil.getContentTypeInfo

data class TourContent(
    val contentId: String,
    val contentTypeInfo: ServiceInfo,
    val createdTime: String,
    val modifiedTime: String,
    val title: String,
    val addr1: String? = null,
    val addr2: String? = null,
    val areaInfo: LocationInfo? = null,
    val sigunguInfo: LocationInfo? = null,
    val majorCategoryInfo: ServiceInfo? = null,
    val mediumCategoryInfo: ServiceInfo? = null,
    val minorCategoryInfo: ServiceInfo? = null,
    val firstImageSrc: String? = null,
    val firstImageThumbnailSrc: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val mapLevel: String? = null,
    val phoneNumber: String? = null,
    val zipCode: String? = null,
    val bookTour: String? = null,
    val copyrightType: String? = null,
)

/**
 * @throws NullPointerException if the content type info is not present in the map.
 */
internal fun TourContentData.toTourContent(
    contentTypeInfoMap: Map<String, ContentTypeInfo>,
    locationInfoMap: Map<String, AreaInfo>,
): TourContent {
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

    return TourContent(
        contentId = contentId,
        contentTypeInfo = contentTypeInfo.serviceInfo,
        createdTime = createdTime,
        modifiedTime = modifiedTime,
        title = title,
        addr1 = addr1,
        addr2 = addr2,
        areaInfo = areaInfo?.locationInfo,
        sigunguInfo = sigunguInfo,
        majorCategoryInfo = majorCatInfo?.serviceInfo,
        mediumCategoryInfo = mediumCatInfo?.serviceInfo,
        minorCategoryInfo = minorCatInfo,
        firstImageSrc = firstImageSrc,
        firstImageThumbnailSrc = firstImageThumbnailSrc,
        longitude = longitude,
        latitude = latitude,
        mapLevel = mapLevel,
        phoneNumber = phoneNumber,
        zipCode = zipCode,
        bookTour = bookTour,
        copyrightType = copyrightType,
    )
}
