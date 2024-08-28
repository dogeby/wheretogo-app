package com.dogeby.wheretogo.core.network.model.tour.requestbody

data class ImgInfoRequestBody(
    val contentId: String,
    val currentPage: Int = 1,
    val numberOfRows: Int = 12,
    val isImgIncluded: Boolean = true,
    val isSubImgIncluded: Boolean = true,
) {

    internal fun toQueryMap(): Map<String, String> {
        return mapOf(
            "contentId" to contentId,
            "pageNo" to currentPage.toString(),
            "numOfRows" to numberOfRows.toString(),
            "imageYN" to isImgIncluded.toYesOrNo(),
            "subImageYN" to isSubImgIncluded.toYesOrNo(),
        )
    }

    private fun Boolean.toYesOrNo() = if (this) "Y" else "N"
}
