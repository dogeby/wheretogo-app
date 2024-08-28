package com.dogeby.wheretogo.core.network

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.model.tour.categoryinfo.NetworkCategoryInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponse
import com.dogeby.wheretogo.core.network.model.tour.imginfo.NetworkImgInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchResponse
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CategoryInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CommonInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.ImgInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.LocationInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.TourInfoByRegionRequestBody
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentResponse

interface TourNetworkDataSource {

    suspend fun fetchTourInfoByRegion(
        tourInfoByRegionRequestBody: TourInfoByRegionRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Result<NetworkTourContentResponse>

    suspend fun fetchFestivalInfo(
        festivalInfoRequestBody: FestivalInfoRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Result<NetworkFestivalResponse>

    suspend fun searchKeyword(
        keywordSearchRequestBody: KeywordSearchRequestBody,
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Result<NetworkKeywordSearchResponse>

    suspend fun fetchCommonInfo(
        commonInfoRequestBody: CommonInfoRequestBody,
    ): Result<NetworkCommonInfoResponse>

    suspend fun fetchLocationInfo(
        locationInfoRequestBody: LocationInfoRequestBody,
    ): Result<NetworkLocationInfoResponse>

    suspend fun fetchCategoryInfo(
        categoryInfoRequestBody: CategoryInfoRequestBody,
    ): Result<NetworkCategoryInfoResponse>

    suspend fun fetchImgInfo(
        imgInfoRequestBody: ImgInfoRequestBody,
    ): Result<NetworkImgInfoResponse>
}
