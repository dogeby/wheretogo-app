package com.dogeby.wheretogo.core.network.model.tour.serviceinfo

import kotlinx.serialization.Serializable

@Serializable
data class NetworkServiceInfoData(
    val rnum: Int,
    val code: String,
    val name: String,
)
