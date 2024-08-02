package com.dogeby.wheretogo.core.domain.tour.locationinfo

import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.AreaInfo
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.LocationInfo
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.toLocationInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class GetLocationInfoMapUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(): Flow<Result<Map<String, AreaInfo>>> {
        return tourRepository
            .getLocationInfoList()
            .map { result ->
                result.mapCatching { areaInfoDataList ->
                    areaInfoDataList.associateBy(
                        keySelector = { it.code },
                    ) { areaInfoData ->
                        val sigunguInfos = getSigunguInfoMap(areaInfoData.code)

                        AreaInfo(
                            locationInfo = LocationInfo(areaInfoData.code, areaInfoData.name),
                            sigunguInfos = sigunguInfos,
                        )
                    }
                }
            }
    }

    private suspend fun getSigunguInfoMap(areaCode: String): Map<String, LocationInfo> {
        val sigunguInfoDataList = tourRepository
            .getLocationInfoList(areaCode)
            .firstOrNull()
            ?.getOrThrow()
            ?: throw NullPointerException()

        return sigunguInfoDataList.associateBy(
            keySelector = { it.code },
        ) { sigunguInfoData ->
            sigunguInfoData.toLocationInfo()
        }
    }
}
