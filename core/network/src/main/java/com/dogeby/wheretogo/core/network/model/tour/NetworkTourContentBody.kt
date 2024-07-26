package com.dogeby.wheretogo.core.network.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTourContentBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkTourContentResult,
)

@Serializable
data class NetworkTourContentResult(
    @SerialName("item")
    val items: List<NetworkTourContentData>,
)
