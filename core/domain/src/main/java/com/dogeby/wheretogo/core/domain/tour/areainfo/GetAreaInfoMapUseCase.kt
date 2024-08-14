package com.dogeby.wheretogo.core.domain.tour.areainfo

import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.AreaInfo
import com.dogeby.wheretogo.core.domain.model.tour.locationinfo.toAreaInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAreaInfoMapUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(): Flow<Result<Map<String, AreaInfo>>> {
        return tourRepository
            .getAreaInfoMap()
            .map { result ->
                result.map { areaInfoMap ->
                    areaInfoMap.mapValues { it.value.toAreaInfo() }
                }
            }
    }
}
