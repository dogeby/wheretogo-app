package com.dogeby.wheretogo.core.domain.tour

import com.dogeby.wheretogo.core.data.repository.TourRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FetchContentTypeInfoMapUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(): Flow<Result<Unit>> {
        return tourRepository.fetchContentTypeInfoMap()
    }
}
