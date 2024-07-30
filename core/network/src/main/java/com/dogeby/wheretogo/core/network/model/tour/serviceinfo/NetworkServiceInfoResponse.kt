package com.dogeby.wheretogo.core.network.model.tour.serviceinfo

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkServiceInfoResponse(
    @SerialName("response")
    val content: NetworkServiceInfoResponseContent,
)

@Serializable
data class NetworkServiceInfoResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkServiceInfoBody,
)
