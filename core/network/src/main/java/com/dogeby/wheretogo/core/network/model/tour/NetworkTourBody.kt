package com.dogeby.wheretogo.core.network.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTourBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkTourResult,
)

@Serializable
data class NetworkTourResult(
    @SerialName("item")
    val items: List<NetworkTourListItem>,
)
