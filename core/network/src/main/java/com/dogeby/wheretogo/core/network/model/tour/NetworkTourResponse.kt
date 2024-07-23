package com.dogeby.wheretogo.core.network.model.tour

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTourResponse(
    val response: NetworkTourResponseContent,
)

@Serializable
data class NetworkTourResponseContent(
    val header: NetworkTourHeader,
    val body: NetworkTourBody,
)
