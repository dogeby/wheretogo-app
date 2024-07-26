package com.dogeby.wheretogo.core.network.model.tour.tourcontent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTourContentResponse(
    @SerialName("response")
    val content: NetworkTourContentResponseContent,
)

@Serializable
data class NetworkTourContentResponseContent(
    val header: NetworkTourContentHeader,
    val body: NetworkTourContentBody,
)
