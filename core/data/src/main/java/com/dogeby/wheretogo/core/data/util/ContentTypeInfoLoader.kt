package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoMap
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ServiceInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.toServiceInfoData
import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManager
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.requestbody.ServiceInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.serviceinfo.NetworkServiceInfoData
import kotlinx.coroutines.flow.first

internal class ContentTypeInfoLoader(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val cachePreferencesManager: CachePreferencesManager,
) {

    suspend fun getContentTypeInfoList(): ContentTypeInfoMap {
        val cachedContentTypeInfoMap = cachePreferencesManager.loadValue(
            key = CONTENT_TYPE_CACHE_KEY,
            deserializer = ContentTypeInfoMap.serializer(),
        ).first()
        if (cachedContentTypeInfoMap.isFailure) {
            val contentTypeInfos = getServiceInfoData().map { contentTypes ->
                contentTypes.associateBy(
                    keySelector = { it.code },
                    valueTransform = { contentType ->
                        val majorCategories = getMajorCategories(contentType.code)
                        ContentTypeInfoData(
                            serviceInfoData = contentType,
                            majorCategories = majorCategories,
                        )
                    },
                )
            }.getOrThrow()
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
        return getServiceInfoData(contentTypeId = contentTypeId)
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
                valueTransform = { majorCategory ->
                    val mediumCategories = getMediumCategories(majorCategory.code)
                    MajorCategoryInfoData(
                        serviceInfoData = majorCategory,
                        mediumCategories = mediumCategories,
                    )
                },
            )
    }

    private suspend fun getMediumCategories(
        majorCategoryId: String,
    ): Map<String, MediumCategoryInfoData> {
        return getServiceInfoData(contentTypeId = majorCategoryId, cat1 = majorCategoryId)
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
                valueTransform = { mediumCategory ->
                    val minorCategories = getMinorCategories(mediumCategory.code)
                    MediumCategoryInfoData(
                        serviceInfoData = mediumCategory,
                        minorCategories = minorCategories,
                    )
                },
            )
    }

    private suspend fun getMinorCategories(
        mediumCategoryId: String,
    ): Map<String, ServiceInfoData> {
        return getServiceInfoData(
            contentTypeId = mediumCategoryId,
            cat1 = mediumCategoryId,
            cat2 = mediumCategoryId,
        )
            .getOrThrow()
            .associateBy { it.code }
    }

    private suspend fun getServiceInfoData(
        contentTypeId: String = "",
        cat1: String = "",
        cat2: String = "",
        cat3: String = "",
    ): Result<List<ServiceInfoData>> {
        return tourNetworkDataSource.fetchServiceInfo(
            ServiceInfoRequestBody(
                contentTypeId = contentTypeId,
                cat1 = cat1,
                cat2 = cat2,
                cat3 = cat3,
            ),
        ).map { response ->
            if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                throw Exception(response.content.header.resultMessage)
            } else {
                response.content.body.result.items.map(NetworkServiceInfoData::toServiceInfoData)
            }
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
        const val CONTENT_TYPE_CACHE_KEY = "contentTypeCacheKey"
    }
}
