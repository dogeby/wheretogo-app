package com.dogeby.wheretogo.core.network.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTourContentHeader(
    @SerialName("resultCode")
    val resultCode: String,

    @SerialName("resultMsg")
    val resultMessage: String,
)
