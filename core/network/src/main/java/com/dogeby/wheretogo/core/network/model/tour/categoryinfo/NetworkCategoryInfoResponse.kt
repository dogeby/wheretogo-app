package com.dogeby.wheretogo.core.network.model.tour.categoryinfo

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCategoryInfoResponse(
    @SerialName("response")
    val content: NetworkCategoryInfoResponseContent,
)

@Serializable
data class NetworkCategoryInfoResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkCategoryInfoBody,
)
