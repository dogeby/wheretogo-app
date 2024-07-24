package com.dogeby.wheretogo.core.network.fake

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourBody
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourHeader
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourListItem
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourResponse
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourResponseContent
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourResult
import javax.inject.Inject
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeTourNetworkDataSource @Inject constructor() : TourNetworkDataSource {

    override suspend fun fetchTourInfoByRegion(
        currentPage: Int,
        numberOfRows: Int,
        contentTypeId: String,
        areaCode: String,
        sigunguCode: String,
        category1: String,
        category2: String,
        category3: String,
        arrangeOption: ArrangeOption,
    ): Result<NetworkTourResponse> {
        val header = NetworkTourHeader(
            resultCode = "0000",
            resultMessage = "OK",
        )
        val body = NetworkTourBody(
            numberOfRows = numberOfRows,
            currentPage = currentPage,
            totalCount = 3742,
            result = NetworkTourResult(
                items = List(numberOfRows) {
                    NetworkTourListItem(
                        contentId = "$currentPage $it",
                        contentTypeId = "38",
                        createdTime = "20111111014944",
                        modifiedTime = "20230407105028",
                        title = "Title $currentPage $it",
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
            NetworkTourResponse(
                response = NetworkTourResponseContent(
                    header = header,
                    body = body,
                ),
            ),
        )
    }
}
