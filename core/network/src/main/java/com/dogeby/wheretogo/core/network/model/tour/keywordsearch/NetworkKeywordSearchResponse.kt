package com.dogeby.wheretogo.core.network.model.tour.keywordsearch

import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkKeywordSearchResponse(
    @SerialName("response")
    val content: NetworkKeywordSearchResponseContent,
)

@Serializable
data class NetworkKeywordSearchResponseContent(
    val header: NetworkTourContentHeader,
    val body: NetworkKeywordSearchBody,
)
