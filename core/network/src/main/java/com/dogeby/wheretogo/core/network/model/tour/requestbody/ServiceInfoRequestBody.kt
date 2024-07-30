package com.dogeby.wheretogo.core.network.model.tour.requestbody

data class ServiceInfoRequestBody(
    val currentPage: Int = 1,
    val numberOfRows: Int = 1000,
    val contentTypeId: String = "",
    val cat1: String = "",
    val cat2: String = "",
    val cat3: String = "",
) {

    internal fun toQueryMap(): Map<String, String> {
        return mapOf(
            "pageNo" to currentPage.toString(),
            "numOfRows" to numberOfRows.toString(),
            "contentTypeId" to contentTypeId,
            "cat1" to cat1,
            "cat2" to cat2,
            "cat3" to cat3,
        )
    }
}
