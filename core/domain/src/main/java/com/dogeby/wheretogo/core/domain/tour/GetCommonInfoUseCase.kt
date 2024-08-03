package com.dogeby.wheretogo.core.domain.tour

import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.CommonInfo
import com.dogeby.wheretogo.core.domain.model.tour.toCommonInfo
import com.dogeby.wheretogo.core.domain.tour.locationinfo.GetLocationInfoMapUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCommonInfoUseCase @Inject constructor(
    private val tourRepository: TourRepository,
    private val getLocationInfoMapUseCase: GetLocationInfoMapUseCase,
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
            getLocationInfoMapUseCase(),
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
        ) { locationInfoMapResult, contentTypeInfoMapResult, commonInfoDataResult ->
            commonInfoDataResult.mapCatching { commonInfoData ->
                val locationInfoMap = locationInfoMapResult.getOrThrow()
                val contentTypeInfoMap = contentTypeInfoMapResult.getOrThrow()

                commonInfoData.toCommonInfo(
                    contentTypeInfoMap = contentTypeInfoMap,
                    locationInfoMap = locationInfoMap,
                )
            }
        }
    }
}
