package com.dogeby.wheretogo.core.network.model.tour.imginfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImgInfoData(
    @SerialName("contentid")
    val contentId: String,

    @SerialName("imgname")
    val imgName: String? = null,

    @SerialName("originimgurl")
    val originImgUrl: String? = null,

    @SerialName("serialnum")
    val serialNum: String? = null,

    @SerialName("cpyrhtDivCd")
    val cpyrhtDivCd: String? = null,

    @SerialName("smallimageurl")
    val smallImgUrl: String? = null,
)
