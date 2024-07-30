package com.dogeby.wheretogo.core.network.model.tour.serviceinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkServiceInfoBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkServiceInfoResult,
)

@Serializable
data class NetworkServiceInfoResult(
    @SerialName("item")
    val items: List<NetworkServiceInfoData>,
)
