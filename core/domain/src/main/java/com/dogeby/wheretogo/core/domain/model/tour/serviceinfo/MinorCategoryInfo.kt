package com.dogeby.wheretogo.core.domain.model.tour.serviceinfo

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MinorCategoryInfoData

data class MinorCategoryInfo(
    val code: String,
    val name: String,
)

internal fun MinorCategoryInfoData.toMinorCategoryInfo() = MinorCategoryInfo(
    code = code,
    name = name,
)
