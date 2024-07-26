package com.dogeby.wheretogo.core.network

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourContentResponse
import com.dogeby.wheretogo.core.network.model.tour.TourInfoByRegionRequestBody

interface TourNetworkDataSource {

    suspend fun fetchTourInfoByRegion(
        tourInfoByRegionRequestBody: TourInfoByRegionRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
    ): Result<NetworkTourContentResponse>
}
