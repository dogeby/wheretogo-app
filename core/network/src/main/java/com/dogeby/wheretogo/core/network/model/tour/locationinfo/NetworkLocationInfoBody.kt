package com.dogeby.wheretogo.core.network.model.tour.locationinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkLocationInfoBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkLocationInfoResult,
)

@Serializable
data class NetworkLocationInfoResult(
    @SerialName("item")
    val items: List<NetworkLocationInfoData>,
)
