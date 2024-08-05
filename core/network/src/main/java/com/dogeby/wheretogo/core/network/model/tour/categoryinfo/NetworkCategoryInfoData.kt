package com.dogeby.wheretogo.core.network.model.tour.categoryinfo

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCategoryInfoData(
    val rnum: Int,
    val code: String,
    val name: String,
)
