package com.dogeby.wheretogo.core.network.model.tour.commoninfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCommonInfoData(
    @SerialName("contentid")
    val contentId: String,

    @SerialName("contenttypeid")
    val contentTypeId: String,

    @SerialName("createdtime")
    val createdTime: String? = null,

    @SerialName("modifiedtime")
    val modifiedTime: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("addr1")
    val addr1: String? = null,

    @SerialName("addr2")
    val addr2: String? = null,

    @SerialName("areacode")
    val areaCode: String? = null,

    @SerialName("sigungucode")
    val sigunguCode: String? = null,

    @SerialName("cat1")
    val category1: String? = null,

    @SerialName("cat2")
    val category2: String? = null,

    @SerialName("cat3")
    val category3: String? = null,

    @SerialName("firstimage")
    val firstImageSrc: String? = null,

    @SerialName("firstimage2")
    val firstImageThumbnailSrc: String? = null,

    @SerialName("mapx")
    val longitude: String? = null,

    @SerialName("mapy")
    val latitude: String? = null,

    @SerialName("mlevel")
    val mapLevel: String? = null,

    @SerialName("tel")
    val phoneNumber: String? = null,

    @SerialName("telname")
    val phoneName: String? = null,

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("zipcode")
    val zipCode: String? = null,

    @SerialName("booktour")
    val bookTour: String? = null,

    @SerialName("cpyrhtDivCd")
    val copyrightType: String? = null,
)
