package com.dogeby.wheretogo.core.network.model.tour.imginfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImgInfoBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkImgInfoResult,
)

@Serializable
data class NetworkImgInfoResult(
    @SerialName("item")
    val items: List<NetworkImgInfoData>,
)
