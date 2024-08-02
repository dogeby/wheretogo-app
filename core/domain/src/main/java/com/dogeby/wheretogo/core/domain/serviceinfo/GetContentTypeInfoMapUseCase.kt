package com.dogeby.wheretogo.core.domain.serviceinfo

import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.ContentTypeInfo
import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.toContentTypeInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContentTypeInfoMapUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(): Flow<Result<Map<String, ContentTypeInfo>>> {
        return tourRepository
            .getContentTypeInfoMap()
            .map { result ->
                result.map { contentTypeInfoDataMap ->
                    contentTypeInfoDataMap.mapValues { it.value.toContentTypeInfo() }
                }
            }
    }
}
