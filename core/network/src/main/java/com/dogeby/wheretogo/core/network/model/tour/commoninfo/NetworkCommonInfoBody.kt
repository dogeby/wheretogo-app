package com.dogeby.wheretogo.core.network.model.tour.commoninfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCommonInfoBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkCommonInfoResult,
)

@Serializable
data class NetworkCommonInfoResult(
    @SerialName("item")
    val items: List<NetworkCommonInfoData>,
)
