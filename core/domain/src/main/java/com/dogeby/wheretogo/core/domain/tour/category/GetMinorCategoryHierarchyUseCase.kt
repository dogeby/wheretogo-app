package com.dogeby.wheretogo.core.domain.tour.category

import com.dogeby.wheretogo.core.domain.model.tour.serviceinfo.CategoryHierarchy
import com.dogeby.wheretogo.core.domain.tour.GetContentTypeInfoMapUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMinorCategoryHierarchyUseCase @Inject constructor(
    private val getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
) {

    operator fun invoke(): Flow<Map<String, CategoryHierarchy>?> {
        return getContentTypeInfoMapUseCase().map { result ->
            val contentTypeInfoMap = result.getOrNull()
            contentTypeInfoMap?.flatMap { (_, contentTypeInfo) ->
                contentTypeInfo.majorCategories.values.flatMap { majorCategoryInfo ->
                    majorCategoryInfo.mediumCategories.values.flatMap { mediumCategoryInfo ->
                        mediumCategoryInfo.minorCategories.keys.map { minorCategoryCode ->
                            minorCategoryCode to CategoryHierarchy(
                                minorCategoryCode = minorCategoryCode,
                                mediumCategoryCode = mediumCategoryInfo.categoryInfo.code,
                                majorCategoryCode = majorCategoryInfo.categoryInfo.code,
                            )
                        }
                    }
                }
            }?.toMap()
        }
    }
}
