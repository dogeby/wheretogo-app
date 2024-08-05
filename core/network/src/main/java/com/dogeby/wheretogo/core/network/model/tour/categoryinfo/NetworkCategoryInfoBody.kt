package com.dogeby.wheretogo.core.network.model.tour.categoryinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCategoryInfoBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkCategoryInfoResult,
)

@Serializable
data class NetworkCategoryInfoResult(
    @SerialName("item")
    val items: List<NetworkCategoryInfoData>,
)
