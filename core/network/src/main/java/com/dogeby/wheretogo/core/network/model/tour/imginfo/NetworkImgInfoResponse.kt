package com.dogeby.wheretogo.core.network.model.tour.imginfo

import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImgInfoResponse(
    @SerialName("response")
    val content: NetworkImgInfoResponseContent,
)

@Serializable
data class NetworkImgInfoResponseContent(
    val header: NetworkTourApiHeader,
    val body: NetworkImgInfoBody,
)
