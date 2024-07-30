package com.dogeby.wheretogo.core.network.fake

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourApiHeader
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoBody
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoData
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoResponseContent
import com.dogeby.wheretogo.core.network.model.tour.commoninfo.NetworkCommonInfoResult
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalBody
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalData
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponse
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponseContent
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResult
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchBody
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchData
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchResponse
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchResponseContent
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchResult
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoBody
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoData
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoResponse
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoResponseContent
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoResult
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CommonInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.LocationInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.TourInfoByRegionRequestBody
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentBody
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentData
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentResponse
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentResponseContent
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentResult
import javax.inject.Inject
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeTourNetworkDataSource @Inject constructor() : TourNetworkDataSource {

    var shouldReturnError: Boolean = false

    override suspend fun fetchTourInfoByRegion(
        tourInfoByRegionRequestBody: TourInfoByRegionRequestBody,
        arrangeOption: ArrangeOption,
    ): Result<NetworkTourContentResponse> {
        if (shouldReturnError) {
            return Result.failure(Exception())
        }
        val header = NetworkTourApiHeader(
            resultCode = "0000",
            resultMessage = "OK",
        )
        val body = NetworkTourContentBody(
            numberOfRows = tourInfoByRegionRequestBody.numberOfRows,
            currentPage = tourInfoByRegionRequestBody.currentPage,
            totalCount = TOTAL_COUNT,
            result = NetworkTourContentResult(
                items = List(tourInfoByRegionRequestBody.numberOfRows) {
                    NetworkTourContentData(
                        contentId = "${tourInfoByRegionRequestBody.currentPage} $it",
                        contentTypeId = "38",
                        createdTime = "20111111014944",
                        modifiedTime = "20230407105028",
                        title = "Title ${tourInfoByRegionRequestBody.currentPage} $it",
                    )
                },
            ),
        )
        return Result.success(
            NetworkTourContentResponse(
                content = NetworkTourContentResponseContent(
                    header = header,
                    body = body,
                ),
            ),
        )
    }

    override suspend fun fetchFestivalInfo(
        festivalInfoRequestBody: FestivalInfoRequestBody,
        arrangeOption: ArrangeOption,
    ): Result<NetworkFestivalResponse> {
        if (shouldReturnError) {
            return Result.failure(Exception())
        }
        val header = NetworkTourApiHeader(
            resultCode = "0000",
            resultMessage = "OK",
        )
        val body = NetworkFestivalBody(
            numberOfRows = festivalInfoRequestBody.numberOfRows,
            currentPage = festivalInfoRequestBody.currentPage,
            totalCount = TOTAL_COUNT,
            result = NetworkFestivalResult(
                items = List(festivalInfoRequestBody.numberOfRows) {
                    NetworkFestivalData(
                        contentId = "${festivalInfoRequestBody.currentPage} $it",
                        contentTypeId = "15",
                        createdTime = "20110418095420",
                        modifiedTime = "20210226112411",
                        title = "Title ${festivalInfoRequestBody.currentPage} $it",
                        eventStartDate = "20210306",
                        eventEndDate = "20211030",
                    )
                },
            ),
        )
        return Result.success(
            NetworkFestivalResponse(
                content = NetworkFestivalResponseContent(
                    header = header,
                    body = body,
                ),
            ),
        )
    }

    override suspend fun searchKeyword(
        keywordSearchRequestBody: KeywordSearchRequestBody,
        arrangeOption: ArrangeOption,
    ): Result<NetworkKeywordSearchResponse> {
        if (shouldReturnError) {
            return Result.failure(Exception())
        }
        val header = NetworkTourApiHeader(
            resultCode = "0000",
            resultMessage = "OK",
        )
        val body = NetworkKeywordSearchBody(
            numberOfRows = keywordSearchRequestBody.numberOfRows,
            currentPage = keywordSearchRequestBody.currentPage,
            totalCount = TOTAL_COUNT,
            result = NetworkKeywordSearchResult(
                items = List(keywordSearchRequestBody.numberOfRows) {
                    NetworkKeywordSearchData(
                        contentId = "${keywordSearchRequestBody.currentPage} $it",
                        contentTypeId = "38",
                        createdTime = "20111111014944",
                        modifiedTime = "20230407105028",
                        title = "Title ${keywordSearchRequestBody.currentPage} " +
                            "$it ${keywordSearchRequestBody.keyword}",
                    )
                },
            ),
        )
        return Result.success(
            NetworkKeywordSearchResponse(
                content = NetworkKeywordSearchResponseContent(
                    header = header,
                    body = body,
                ),
            ),
        )
    }

    override suspend fun fetchCommonInfo(
        commonInfoRequestBody: CommonInfoRequestBody,
    ): Result<NetworkCommonInfoResponse> {
        if (shouldReturnError) {
            return Result.failure(Exception())
        }
        val header = NetworkTourApiHeader(
            resultCode = "0000",
            resultMessage = "OK",
        )
        val body = NetworkCommonInfoBody(
            numberOfRows = 1,
            currentPage = 1,
            totalCount = 1,
            result = NetworkCommonInfoResult(
                items = List(1) {
                    NetworkCommonInfoData(
                        contentId = "$it",
                        contentTypeId = "38",
                        title = "Title $it",
                    )
                },
            ),
        )
        return Result.success(
            NetworkCommonInfoResponse(
                content = NetworkCommonInfoResponseContent(
                    header = header,
                    body = body,
                ),
            ),
        )
    }

    override suspend fun fetchLocationInfo(
        locationInfoRequestBody: LocationInfoRequestBody,
    ): Result<NetworkLocationInfoResponse> {
        if (shouldReturnError) {
            return Result.failure(Exception())
        }
        val header = NetworkTourApiHeader(
            resultCode = "0000",
            resultMessage = "OK",
        )
        val body = NetworkLocationInfoBody(
            numberOfRows = 1,
            currentPage = 1,
            totalCount = 1,
            result = NetworkLocationInfoResult(
                items = List(1) {
                    NetworkLocationInfoData(
                        code = "$it",
                        name = "Name $it",
                        rnum = it,
                    )
                },
            ),
        )
        return Result.success(
            NetworkLocationInfoResponse(
                content = NetworkLocationInfoResponseContent(
                    header = header,
                    body = body,
                ),
            ),
        )
    }

    companion object {
        const val TOTAL_COUNT = 100
    }
}
