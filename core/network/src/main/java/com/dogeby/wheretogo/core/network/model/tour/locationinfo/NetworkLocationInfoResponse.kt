package com.dogeby.wheretogo.core.network.model.tour.locationinfo

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkLocationInfoResponse(
    @SerialName("response")
    val content: NetworkLocationInfoResponseContent,
)

@Serializable
data class NetworkLocationInfoResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkLocationInfoBody,
)
