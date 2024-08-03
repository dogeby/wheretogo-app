package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ServiceInfoData

data class ServiceInfo(
    val code: String,
    val name: String,
)

internal fun ServiceInfoData.toServiceInfo() = ServiceInfo(
    code = code,
    name = name,
)
