package com.dogeby.wheretogo.core.domain.locationinfo

import com.dogeby.wheretogo.core.data.model.tour.LocationInfoData
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.LocationInfo
import com.dogeby.wheretogo.core.domain.model.tour.toLocationInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLocationInfosUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(areaCode: String = ""): Flow<Result<List<LocationInfo>>> {
        return tourRepository
            .getLocationInfoList(areaCode)
            .map { result ->
                result.map { locationInfoDataList ->
                    locationInfoDataList.map(LocationInfoData::toLocationInfo)
                }
            }
    }
}
