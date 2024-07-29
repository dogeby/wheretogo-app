package com.dogeby.wheretogo.core.network.model.tour.commoninfo

import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCommonInfoResponse(
    @SerialName("response")
    val content: NetworkCommonInfoResponseContent,
)

@Serializable
data class NetworkCommonInfoResponseContent(
    val header: NetworkTourContentHeader,
    val body: NetworkCommonInfoBody,
)
