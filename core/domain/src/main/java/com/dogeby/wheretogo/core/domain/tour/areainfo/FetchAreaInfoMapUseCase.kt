package com.dogeby.wheretogo.core.domain.tour.areainfo

import com.dogeby.wheretogo.core.data.repository.TourRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FetchAreaInfoMapUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(): Flow<Result<Unit>> {
        return tourRepository.fetchAreaInfoMap()
    }
}
