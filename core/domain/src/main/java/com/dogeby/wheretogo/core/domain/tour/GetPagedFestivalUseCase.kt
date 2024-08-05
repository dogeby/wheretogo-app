package com.dogeby.wheretogo.core.domain.tour

import androidx.paging.PagingData
import androidx.paging.map
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.Festival
import com.dogeby.wheretogo.core.domain.model.tour.toFestival
import com.dogeby.wheretogo.core.domain.tour.locationinfo.GetLocationInfoMapUseCase
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class GetPagedFestivalUseCase @Inject constructor(
    private val tourRepository: TourRepository,
    private val getLocationInfoMapUseCase: GetLocationInfoMapUseCase,
    private val getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
) {

    operator fun invoke(
        eventStartDate: Instant = Clock.System.now(),
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<Result<PagingData<Festival>>> {
        return combine(
            getLocationInfoMapUseCase(),
            getContentTypeInfoMapUseCase(),
            tourRepository.getPagedFestivalInfo(
                eventStartDate = eventStartDate,
                arrangeOption = arrangeOption,
            ),
        ) { locationInfoMapResult, contentTypeInfoMapResult, pagedFestivalData ->
            runCatching {
                val locationInfoMap = locationInfoMapResult.getOrThrow()
                val contentTypeInfoMap = contentTypeInfoMapResult.getOrThrow()

                pagedFestivalData.map { festivalData ->
                    festivalData.toFestival(
                        contentTypeInfoMap = contentTypeInfoMap,
                        locationInfoMap = locationInfoMap,
                    )
                }
            }
        }
    }
}
