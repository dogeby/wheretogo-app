package com.dogeby.wheretogo.core.network.model.tour

data class TourInfoRequestBody(
    val numberOfRows: Int,
    val currentPage: Int,
    val mobileOs: String,
    val mobileApp: String,
    val serviceKey: String,
    val responseType: String,
    val arrange: String,
    val contentTypeId: String,
    val areaCode: String,
    val sigunguCode: String,
    val category1: String,
    val category2: String,
    val category3: String,
) {

    fun toQueryMap(): Map<String, String> {
        return mapOf(
            "numOfRows" to numberOfRows.toString(),
            "pageNo" to currentPage.toString(),
            "MobileOS" to mobileOs,
            "MobileApp" to mobileApp,
            "serviceKey" to serviceKey,
            "_type" to responseType,
            "arrange" to arrange,
            "contentTypeId" to contentTypeId,
            "areaCode" to areaCode,
            "sigunguCode" to sigunguCode,
            "cat1" to category1,
            "cat2" to category2,
            "cat3" to category3,
        )
    }
}
