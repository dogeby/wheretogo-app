package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import com.dogeby.wheretogo.core.network.model.tour.serviceinfo.NetworkServiceInfoData
import kotlinx.serialization.Serializable

@Serializable
data class ServiceInfoData(
    val rnum: Int,
    val code: String,
    val name: String,
)

internal fun NetworkServiceInfoData.toServiceInfoData() = ServiceInfoData(
    rnum = rnum,
    code = code,
    name = name,
)
