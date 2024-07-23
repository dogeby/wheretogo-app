package com.dogeby.wheretogo.core.network

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourResponse

interface TourNetworkDataSource {

    suspend fun fetchTourInfoByRegion(
        currentPage: Int = 1,
        numberOfRows: Int = 12,
        contentTypeId: String = "",
        areaCode: String = "",
        sigunguCode: String = "",
        category1: String = "",
        category2: String = "",
        category3: String = "",
        arrangeOption: ArrangeOption = ArrangeOption.TITLE,
    ): Result<NetworkTourResponse>
}
