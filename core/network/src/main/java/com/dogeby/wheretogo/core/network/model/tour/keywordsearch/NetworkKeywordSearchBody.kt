package com.dogeby.wheretogo.core.network.model.tour.keywordsearch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkKeywordSearchBody(
    @SerialName("numOfRows")
    val numberOfRows: Int,

    @SerialName("pageNo")
    val currentPage: Int,

    @SerialName("totalCount")
    val totalCount: Int,

    @SerialName("items")
    val result: NetworkKeywordSearchResult,
)

@Serializable
data class NetworkKeywordSearchResult(
    @SerialName("item")
    val items: List<NetworkKeywordSearchData>,
)
