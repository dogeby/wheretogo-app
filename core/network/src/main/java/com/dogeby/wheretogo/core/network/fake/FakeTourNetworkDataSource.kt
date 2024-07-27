package com.dogeby.wheretogo.core.network.fake

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.TourInfoByRegionRequestBody
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalBody
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalData
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponse
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponseContent
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResult
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentBody
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentData
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentHeader
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
        val header = NetworkTourContentHeader(
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
                        addr1 = null,
                        addr2 = null,
                        areaCode = null,
                        sigunguCode = null,
                        category1 = null,
                        category2 = null,
                        category3 = null,
                        firstImageSrc = null,
                        firstImageThumbnailSrc = null,
                        longitude = null,
                        latitude = null,
                        mapLevel = null,
                        phoneNumber = null,
                        zipCode = null,
                        bookTour = null,
                        copyrightType = null,
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
        val header = NetworkTourContentHeader(
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
                        addr1 = null,
                        addr2 = null,
                        areaCode = null,
                        sigunguCode = null,
                        category1 = null,
                        category2 = null,
                        category3 = null,
                        firstImageSrc = null,
                        firstImageThumbnailSrc = null,
                        longitude = null,
                        latitude = null,
                        mapLevel = null,
                        phoneNumber = null,
                        zipCode = null,
                        bookTour = null,
                        copyrightType = null,
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

    companion object {
        const val TOTAL_COUNT = 100
    }
}
