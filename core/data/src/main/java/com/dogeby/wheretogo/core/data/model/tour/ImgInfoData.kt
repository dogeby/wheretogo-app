package com.dogeby.wheretogo.core.data.model.tour

import com.dogeby.wheretogo.core.network.model.tour.imginfo.NetworkImgInfoData

data class ImgInfoData(
    val contentId: String,
    val imgName: String? = null,
    val originImgUrl: String? = null,
    val serialNum: String? = null,
    val cpyrhtDivCd: String? = null,
    val smallImgUrl: String? = null,
)

internal fun NetworkImgInfoData.toImgInfoData() = ImgInfoData(
    contentId = contentId,
    imgName = imgName,
    originImgUrl = originImgUrl,
    serialNum = serialNum,
    cpyrhtDivCd = cpyrhtDivCd,
    smallImgUrl = smallImgUrl,
)
