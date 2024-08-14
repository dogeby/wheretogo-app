package com.dogeby.wheretogo.core.domain.tour

import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.CommonInfo
import com.dogeby.wheretogo.core.domain.model.tour.toCommonInfo
import com.dogeby.wheretogo.core.domain.tour.areainfo.GetAreaInfoMapUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCommonInfoUseCase @Inject constructor(
    private val tourRepository: TourRepository,
    private val getAreaInfoMapUseCase: GetAreaInfoMapUseCase,
    private val getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
) {

    operator fun invoke(
        contentId: String,
        contentTypeId: String = "",
        isDefaultInfoIncluded: Boolean = true,
        isFirstImgIncluded: Boolean = true,
        isAreaCodeIncluded: Boolean = true,
        isCategoryIncluded: Boolean = true,
        isAddrInfoIncluded: Boolean = true,
        isMapInfoIncluded: Boolean = true,
        isOverviewIncluded: Boolean = true,
    ): Flow<Result<CommonInfo>> {
        return combine(
            getAreaInfoMapUseCase(),
            getContentTypeInfoMapUseCase(),
            tourRepository.getCommonInfo(
                contentId = contentId,
                contentTypeId = contentTypeId,
                isDefaultInfoIncluded = isDefaultInfoIncluded,
                isFirstImgIncluded = isFirstImgIncluded,
                isAreaCodeIncluded = isAreaCodeIncluded,
                isCategoryIncluded = isCategoryIncluded,
                isAddrInfoIncluded = isAddrInfoIncluded,
                isMapInfoIncluded = isMapInfoIncluded,
                isOverviewIncluded = isOverviewIncluded,
            ),
        ) { areaInfoMapResult, contentTypeInfoMapResult, commonInfoDataResult ->
            commonInfoDataResult.mapCatching { commonInfoData ->
                val areaInfoMap = areaInfoMapResult.getOrThrow()
                val contentTypeInfoMap = contentTypeInfoMapResult.getOrThrow()

                commonInfoData.toCommonInfo(
                    contentTypeInfoMap = contentTypeInfoMap,
                    areaInfoMap = areaInfoMap,
                )
            }
        }
    }
}
