package com.dogeby.wheretogo.core.domain.tour

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.dogeby.wheretogo.core.data.model.tour.FestivalData
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.Festival
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.CategoryHierarchy
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
        filterCategoryHierarchy: CategoryHierarchy = CategoryHierarchy(),
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

                val filteredFestivalDataList =
                    pagedFestivalData.filterByCategoryHierarchy(filterCategoryHierarchy)

                filteredFestivalDataList.map { festivalData ->
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

    private fun PagingData<FestivalData>.filterByCategoryHierarchy(
        categoryHierarchy: CategoryHierarchy,
    ): PagingData<FestivalData> {
        return this.filterByCategory({ it.category1 ?: "" }, categoryHierarchy.majorCategoryCode)
            .filterByCategory({ it.category2 ?: "" }, categoryHierarchy.mediumCategoryCode)
            .filterByCategory({ it.category3 ?: "" }, categoryHierarchy.minorCategoryCode)
    }

    private inline fun PagingData<FestivalData>.filterByCategory(
        crossinline categorySelector: (FestivalData) -> String,
        categoryCode: String,
    ): PagingData<FestivalData> {
        return if (categoryCode.isNotBlank()) {
            this.filter { categorySelector(it) == categoryCode }
        } else {
            this
        }
    }
}
