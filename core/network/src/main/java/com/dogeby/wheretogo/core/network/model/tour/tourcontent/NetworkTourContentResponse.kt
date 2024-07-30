package com.dogeby.wheretogo.core.network.model.tour.tourcontent

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTourContentResponse(
    @SerialName("response")
    val content: NetworkTourContentResponseContent,
)

@Serializable
data class NetworkTourContentResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkTourContentBody,
)
