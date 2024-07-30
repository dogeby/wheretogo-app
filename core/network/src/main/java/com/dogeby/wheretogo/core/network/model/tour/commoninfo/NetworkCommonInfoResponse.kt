package com.dogeby.wheretogo.core.network.model.tour.commoninfo

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCommonInfoResponse(
    @SerialName("response")
    val content: NetworkCommonInfoResponseContent,
)

@Serializable
data class NetworkCommonInfoResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkCommonInfoBody,
)
