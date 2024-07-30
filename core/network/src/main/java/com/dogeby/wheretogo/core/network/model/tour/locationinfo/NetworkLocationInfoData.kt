package com.dogeby.wheretogo.core.network.model.tour.locationinfo

import kotlinx.serialization.Serializable

@Serializable
data class NetworkLocationInfoData(
    val rnum: Int,
    val code: String,
    val name: String,
)
