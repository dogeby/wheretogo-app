package com.dogeby.wheretogo.core.domain.tour

import androidx.paging.PagingData
import androidx.paging.map
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.TourContent
import com.dogeby.wheretogo.core.domain.model.tour.toTourContent
import com.dogeby.wheretogo.core.domain.tour.areainfo.GetAreaInfoMapUseCase
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPagedTourContentUseCase @Inject constructor(
    private val tourRepository: TourRepository,
    private val getAreaInfoMapUseCase: GetAreaInfoMapUseCase,
    private val getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
) {

    operator fun invoke(
        contentTypeId: String = "",
        areaCode: String = "",
        sigunguCode: String = "",
        majorCategoryCode: String = "",
        mediumCategoryCode: String = "",
        minorCategoryCode: String = "",
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<PagingData<TourContent>> {
        return combine(
            getAreaInfoMapUseCase(),
            getContentTypeInfoMapUseCase(),
            tourRepository.getPagedTourInfoByRegion(
                contentTypeId = contentTypeId,
                areaCode = areaCode,
                sigunguCode = sigunguCode,
                category1 = majorCategoryCode,
                category2 = mediumCategoryCode,
                category3 = minorCategoryCode,
                arrangeOption = arrangeOption,
            ),
        ) { areaInfoMapResult, contentTypeInfoMapResult, pagedTourInfoData ->
            try {
                val areaInfoMap = areaInfoMapResult.getOrThrow()
                val contentTypeInfoMap = contentTypeInfoMapResult.getOrThrow()

                pagedTourInfoData.map { tourContentData ->
                    tourContentData.toTourContent(
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
