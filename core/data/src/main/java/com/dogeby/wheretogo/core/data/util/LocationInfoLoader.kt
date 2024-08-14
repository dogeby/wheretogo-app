package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.data.model.tour.locationinfo.AreaInfoData
import com.dogeby.wheretogo.core.data.model.tour.locationinfo.AreaInfoMap
import com.dogeby.wheretogo.core.data.model.tour.locationinfo.LocationInfoData
import com.dogeby.wheretogo.core.data.model.tour.locationinfo.toLocationInfoData
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoData
import com.dogeby.wheretogo.core.network.model.tour.requestbody.LocationInfoRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocationInfoLoader @Inject constructor(
    private val tourNetworkDataSource: TourNetworkDataSource,
) {

    /**
     * @throws Exception If the API call fails
     */
    suspend fun fetchAreaInfoMap(): AreaInfoMap {
        val areaInfos = fetchLocationInfoData()
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
            ) {
                val sigunguInfos = fetchSigunguInfoMap(it.code)
                AreaInfoData(
                    locationInfo = it,
                    sigunguInfos = sigunguInfos,
                )
            }

        return AreaInfoMap(areaInfos)
    }

    /**
     * @throws Exception If the API call fails
     */
    private suspend fun fetchSigunguInfoMap(areaCode: String): Map<String, LocationInfoData> {
        return fetchLocationInfoData(areaCode)
            .getOrThrow()
            .associateBy { it.code }
    }

    private suspend fun fetchLocationInfoData(
        areaCode: String = "",
    ): Result<List<LocationInfoData>> {
        return tourNetworkDataSource.fetchLocationInfo(
            LocationInfoRequestBody(
                areaCode = areaCode,
            ),
        ).mapCatching { response ->
            if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                throw Exception(response.content.header.resultMessage)
            } else {
                response.content.body.result.items.map(NetworkLocationInfoData::toLocationInfoData)
            }
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
    }
}
