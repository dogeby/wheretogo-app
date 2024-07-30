package com.dogeby.wheretogo.core.network.model.tour

data class LocationInfoRequestBody(
    val currentPage: Int = 1,
    val numberOfRows: Int = 1000,
    val areaCode: String = "",
) {

    internal fun toQueryMap(): Map<String, String> {
        return mapOf(
            "pageNo" to currentPage.toString(),
            "numOfRows" to numberOfRows.toString(),
            "areaCode" to areaCode,
        )
    }
}
