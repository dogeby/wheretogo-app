package com.dogeby.wheretogo.core.network.model.tour.keywordsearch

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkKeywordSearchResponse(
    @SerialName("response")
    val content: NetworkKeywordSearchResponseContent,
)

@Serializable
data class NetworkKeywordSearchResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkKeywordSearchBody,
)
