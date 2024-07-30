package com.dogeby.wheretogo.core.network.model.tour.requestbody

data class CommonInfoRequestBody(
    val contentId: String,
    val contentTypeId: String = "",
    val isDefaultInfoIncluded: Boolean = true,
    val isFirstImgIncluded: Boolean = true,
    val isAreaCodeIncluded: Boolean = true,
    val isCategoryIncluded: Boolean = true,
    val isAddrInfoIncluded: Boolean = true,
    val isMapInfoIncluded: Boolean = true,
    val isOverviewIncluded: Boolean = true,
) {

    internal fun toQueryMap(): Map<String, String> {
        return mapOf(
            "contentId" to contentId,
            "contentTypeId" to contentTypeId,
            "defaultYN" to isDefaultInfoIncluded.toYesOrNo(),
            "firstImageYN" to isFirstImgIncluded.toYesOrNo(),
            "areacodeYN" to isAreaCodeIncluded.toYesOrNo(),
            "catcodeYN" to isCategoryIncluded.toYesOrNo(),
            "addrinfoYN" to isAddrInfoIncluded.toYesOrNo(),
            "mapinfoYN" to isMapInfoIncluded.toYesOrNo(),
            "overviewYN" to isOverviewIncluded.toYesOrNo(),
        )
    }

    private fun Boolean.toYesOrNo() = if (this) "Y" else "N"
}
