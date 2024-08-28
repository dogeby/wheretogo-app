package com.dogeby.wheretogo.core.domain.model.tour

import com.dogeby.wheretogo.core.data.model.tour.ImgInfoData

data class ImgInfo(
    val contentId: String,
    val imgName: String? = null,
    val originImgUrl: String? = null,
    val serialNum: String? = null,
    val cpyrhtDivCd: String? = null,
    val smallImgUrl: String? = null,
)

internal fun ImgInfoData.toImgInfo() = ImgInfo(
    contentId = contentId,
    imgName = imgName,
    originImgUrl = originImgUrl,
    serialNum = serialNum,
    cpyrhtDivCd = cpyrhtDivCd,
    smallImgUrl = smallImgUrl,
)
