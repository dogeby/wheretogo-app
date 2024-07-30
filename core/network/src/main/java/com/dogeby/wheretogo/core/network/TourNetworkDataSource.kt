package com.dogeby.wheretogo.core.network

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.model.tour.CommonInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.LocationInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.TourInfoByRegionRequestBody
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponse
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchResponse
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentResponse

interface TourNetworkDataSource {

    suspend fun fetchTourInfoByRegion(
        tourInfoByRegionRequestBody: TourInfoByRegionRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
    ): Result<NetworkTourContentResponse>

    suspend fun fetchFestivalInfo(
        festivalInfoRequestBody: FestivalInfoRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
    ): Result<NetworkFestivalResponse>

    suspend fun searchKeyword(
        keywordSearchRequestBody: KeywordSearchRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
    ): Result<NetworkKeywordSearchResponse>

    suspend fun fetchCommonInfo(
        commonInfoRequestBody: CommonInfoRequestBody,
    ): Result<NetworkCommonInfoResponse>

    suspend fun fetchLocationInfo(
        locationInfoRequestBody: LocationInfoRequestBody,
    ): Result<NetworkLocationInfoResponse>
}
