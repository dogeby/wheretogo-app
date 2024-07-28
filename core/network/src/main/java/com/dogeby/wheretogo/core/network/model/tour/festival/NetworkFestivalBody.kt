package com.dogeby.wheretogo.core.network.model.tour.festival

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFestivalBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkFestivalResult,
)

@Serializable
data class NetworkFestivalResult(
    @SerialName("item")
    val items: List<NetworkFestivalData>,
)
