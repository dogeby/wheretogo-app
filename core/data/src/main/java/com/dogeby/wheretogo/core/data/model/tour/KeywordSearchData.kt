package com.dogeby.wheretogo.core.data.model.tour

import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchData

data class KeywordSearchData(
    val contentId: String,
    val contentTypeId: String,
    val createdTime: String,
    val modifiedTime: String,
    val title: String,
    val addr1: String? = null,
    val addr2: String? = null,
    val areaCode: String? = null,
    val sigunguCode: String? = null,
    val category1: String? = null,
    val category2: String? = null,
    val category3: String? = null,
    val firstImageSrc: String? = null,
    val firstImageThumbnailSrc: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val mapLevel: String? = null,
    val phoneNumber: String? = null,
    val bookTour: String? = null,
    val copyrightType: String? = null,
)

internal fun NetworkKeywordSearchData.toKeywordSearchData() = KeywordSearchData(
    contentId = contentId,
    contentTypeId = contentTypeId,
    createdTime = createdTime,
    modifiedTime = modifiedTime,
    title = title,
    addr1 = addr1,
    addr2 = addr2,
    areaCode = areaCode,
    sigunguCode = sigunguCode,
    category1 = category1,
    category2 = category2,
    category3 = category3,
    firstImageSrc = firstImageSrc,
    firstImageThumbnailSrc = firstImageThumbnailSrc,
    longitude = longitude,
    latitude = latitude,
    mapLevel = mapLevel,
    phoneNumber = phoneNumber,
    bookTour = bookTour,
    copyrightType = copyrightType,
)
