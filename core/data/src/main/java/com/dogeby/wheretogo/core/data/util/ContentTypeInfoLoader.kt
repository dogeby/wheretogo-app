package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MajorCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MediumCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.MinorCategoryInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ServiceInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.toServiceInfoData
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.requestbody.ServiceInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.serviceinfo.NetworkServiceInfoData

internal class ContentTypeInfoLoader(
    private val tourNetworkDataSource: TourNetworkDataSource,
) {

    suspend fun getContentTypeInfoList(): Map<String, ContentTypeInfoData> {
        return getServiceInfoData().map { contentTypes ->
            contentTypes.associateBy(
                keySelector = { it.code },
                valueTransform = { contentType ->
                    val majorCategories = getMajorCategories(contentType.code)
                    ContentTypeInfoData(
                        code = contentType.code,
                        name = contentType.name,
                        majorCategories = majorCategories,
                    )
                },
            )
        }.getOrThrow()
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
                        code = majorCategory.code,
                        name = majorCategory.name,
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
                        code = mediumCategory.code,
                        name = mediumCategory.name,
                        minorCategories = minorCategories,
                    )
                },
            )
    }

    private suspend fun getMinorCategories(
        mediumCategoryId: String,
    ): Map<String, MinorCategoryInfoData> {
        return getServiceInfoData(
            contentTypeId = mediumCategoryId,
            cat1 = mediumCategoryId,
            cat2 = mediumCategoryId,
        )
            .getOrThrow()
            .associateBy(
                keySelector = { it.code },
            ) {
                MinorCategoryInfoData(
                    code = it.code,
                    name = it.name,
                )
            }
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
    }
}
