package com.dogeby.wheretogo.core.domain.tour

import androidx.paging.PagingData
import androidx.paging.map
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.Festival
import com.dogeby.wheretogo.core.domain.model.tour.toFestival
import com.dogeby.wheretogo.core.domain.tour.areainfo.GetAreaInfoMapUseCase
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class GetPagedFestivalUseCase @Inject constructor(
    private val tourRepository: TourRepository,
    private val getAreaInfoMapUseCase: GetAreaInfoMapUseCase,
    private val getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
) {

    operator fun invoke(
        eventStartDate: Instant = Clock.System.now(),
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<PagingData<Festival>> {
        return combine(
            getAreaInfoMapUseCase(),
            getContentTypeInfoMapUseCase(),
            tourRepository.getPagedFestivalInfo(
                eventStartDate = eventStartDate,
                arrangeOption = arrangeOption,
            ),
        ) { areaInfoMapResult, contentTypeInfoMapResult, pagedFestivalData ->
            try {
                val areaInfoMap = areaInfoMapResult.getOrThrow()
                val contentTypeInfoMap = contentTypeInfoMapResult.getOrThrow()

                pagedFestivalData.map { festivalData ->
                    festivalData.toFestival(
                        contentTypeInfoMap = contentTypeInfoMap,
                        areaInfoMap = areaInfoMap,
                    )
                }
            } catch (e: Exception) {
                PagingData.empty()
            }
        }
    }
}
