package com.dogeby.wheretogo.core.network.model.tour

data class KeywordSearchRequestBody(
    val keyword: String,
    val currentPage: Int = 1,
    val numberOfRows: Int = 12,
    val contentTypeId: String = "",
    val areaCode: String = "",
    val sigunguCode: String = "",
    val category1: String = "",
    val category2: String = "",
    val category3: String = "",
) {

    internal fun toQueryMap(): Map<String, String> {
        return mapOf(
            "keyword" to keyword,
            "pageNo" to currentPage.toString(),
            "numOfRows" to numberOfRows.toString(),
            "contentTypeId" to contentTypeId,
            "areaCode" to areaCode,
            "sigunguCode" to sigunguCode,
            "cat1" to category1,
            "cat2" to category2,
            "cat3" to category3,
        )
    }
}
