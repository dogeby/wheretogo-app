package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.CategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoMap
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.toCategoryInfoData
import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManager
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.categoryinfo.NetworkCategoryInfoData
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CategoryInfoRequestBody
import kotlinx.coroutines.flow.first

internal class ContentTypeInfoLoader(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val cachePreferencesManager: CachePreferencesManager,
) {

    suspend fun getContentTypeInfoList(
        contentType: List<TourContentType> = TourContentType.entries,
    ): ContentTypeInfoMap {
        val cachedContentTypeInfoMap = cachePreferencesManager.loadValue(
            key = CONTENT_TYPE_CACHE_KEY,
            deserializer = ContentTypeInfoMap.serializer(),
        ).first()
        if (cachedContentTypeInfoMap.isFailure) {
            val contentTypeInfos = contentType.associateBy(
                keySelector = { it.id },
            ) {
                val majorCategories = getMajorCategories(it.id)
                ContentTypeInfoData(
                    contentType = it,
                    majorCategories = majorCategories,
                )
            }
            val contentTypeInfoMap = ContentTypeInfoMap(contentTypeInfos)
            cachePreferencesManager.saveValue(
                key = CONTENT_TYPE_CACHE_KEY,
                value = contentTypeInfoMap,
                serializer = ContentTypeInfoMap.serializer(),
            )
            return contentTypeInfoMap
        } else {
            return cachedContentTypeInfoMap.getOrThrow()
        }
    }

    private suspend fun getMajorCategories(
        contentTypeId: String,
    ): Map<String, MajorCategoryInfoData> {
        return getCategoryInfoData(contentTypeId = contentTypeId)
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
                valueTransform = { majorCategory ->
                    val mediumCategories = getMediumCategories(
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

    private suspend fun getMediumCategories(
        contentTypeId: String,
        majorCategoryId: String,
    ): Map<String, MediumCategoryInfoData> {
        return getCategoryInfoData(
            contentTypeId = contentTypeId,
            cat1 = majorCategoryId,
        )
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
                valueTransform = { mediumCategory ->
                    val minorCategories = getMinorCategories(
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

    private suspend fun getMinorCategories(
        contentTypeId: String,
        majorCategoryId: String,
        mediumCategoryId: String,
    ): Map<String, CategoryInfoData> {
        return getCategoryInfoData(
            contentTypeId = contentTypeId,
            cat1 = majorCategoryId,
            cat2 = mediumCategoryId,
        )
            .getOrThrow()
            .associateBy { it.code }
    }

    private suspend fun getCategoryInfoData(
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
        ).map { response ->
            if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                throw Exception(response.content.header.resultMessage)
            } else {
                response.content.body.result.items.map(NetworkCategoryInfoData::toCategoryInfoData)
            }
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
        const val CONTENT_TYPE_CACHE_KEY = "contentTypeCacheKey"
    }
}
