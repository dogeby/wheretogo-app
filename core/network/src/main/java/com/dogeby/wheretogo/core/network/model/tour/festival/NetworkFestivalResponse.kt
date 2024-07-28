package com.dogeby.wheretogo.core.network.model.tour.festival

import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFestivalResponse(
    @SerialName("response")
    val content: NetworkFestivalResponseContent,
)

@Serializable
data class NetworkFestivalResponseContent(
    val header: NetworkTourContentHeader,
    val body: NetworkFestivalBody,
)
