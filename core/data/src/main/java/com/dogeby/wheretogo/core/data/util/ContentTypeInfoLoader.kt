package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.CategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoMap
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.toCategoryInfoData
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.categoryinfo.NetworkCategoryInfoData
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CategoryInfoRequestBody

internal class ContentTypeInfoLoader(
    private val tourNetworkDataSource: TourNetworkDataSource,
) {

    suspend fun fetchContentTypeInfoList(
        contentType: List<TourContentType> = TourContentType.entries,
    ): ContentTypeInfoMap {
        val contentTypeInfos = contentType.associateBy(
            keySelector = { it.id },
        ) {
            val majorCategories = fetchMajorCategories(it.id)
            ContentTypeInfoData(
                contentType = it,
                majorCategories = majorCategories,
            )
        }
        val contentTypeInfoMap = ContentTypeInfoMap(contentTypeInfos)

        return contentTypeInfoMap
    }

    private suspend fun fetchMajorCategories(
        contentTypeId: String,
    ): Map<String, MajorCategoryInfoData> {
        return fetchCategoryInfoData(contentTypeId = contentTypeId)
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
                valueTransform = { majorCategory ->
                    val mediumCategories = fetchMediumCategories(
                        contentTypeId = contentTypeId,
                        majorCategoryId = majorCategory.code,
                    )
                    MajorCategoryInfoData(
                        categoryInfoData = majorCategory,
                        mediumCategories = mediumCategories,
                    )
                },
            )
    }

    private suspend fun fetchMediumCategories(
        contentTypeId: String,
        majorCategoryId: String,
    ): Map<String, MediumCategoryInfoData> {
        return fetchCategoryInfoData(
            contentTypeId = contentTypeId,
            cat1 = majorCategoryId,
        )
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
                valueTransform = { mediumCategory ->
                    val minorCategories = fetchMinorCategories(
                        contentTypeId = contentTypeId,
                        majorCategoryId = majorCategoryId,
                        mediumCategoryId = mediumCategory.code,
                    )
                    MediumCategoryInfoData(
                        categoryInfoData = mediumCategory,
                        minorCategories = minorCategories,
                    )
                },
            )
    }

    private suspend fun fetchMinorCategories(
        contentTypeId: String,
        majorCategoryId: String,
        mediumCategoryId: String,
    ): Map<String, CategoryInfoData> {
        return fetchCategoryInfoData(
            contentTypeId = contentTypeId,
            cat1 = majorCategoryId,
            cat2 = mediumCategoryId,
        )
            .getOrThrow()
            .associateBy { it.code }
    }

    private suspend fun fetchCategoryInfoData(
        contentTypeId: String = "",
        cat1: String = "",
        cat2: String = "",
        cat3: String = "",
    ): Result<List<CategoryInfoData>> {
        return tourNetworkDataSource.fetchCategoryInfo(
            CategoryInfoRequestBody(
                contentTypeId = contentTypeId,
                cat1 = cat1,
                cat2 = cat2,
                cat3 = cat3,
            ),
        ).mapCatching { response ->
            if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                throw Exception(response.content.header.resultMessage)
            } else {
                response.content.body.result.items.map(NetworkCategoryInfoData::toCategoryInfoData)
            }
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
    }
}
