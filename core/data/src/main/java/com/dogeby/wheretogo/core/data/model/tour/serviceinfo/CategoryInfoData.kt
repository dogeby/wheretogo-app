package com.dogeby.wheretogo.core.data.model.tour.serviceinfo

import com.dogeby.wheretogo.core.network.model.tour.categoryinfo.NetworkCategoryInfoData
import kotlinx.serialization.Serializable

@Serializable
data class CategoryInfoData(
    val rnum: Int,
    val code: String,
    val name: String,
)

internal fun NetworkCategoryInfoData.toCategoryInfoData() = CategoryInfoData(
    rnum = rnum,
    code = code,
    name = name,
)
