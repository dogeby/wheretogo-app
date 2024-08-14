package com.dogeby.wheretogo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.tour.CommonInfoData
import com.dogeby.wheretogo.core.data.model.tour.FestivalData
import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchResultData
import com.dogeby.wheretogo.core.data.model.tour.LocationInfoData
import com.dogeby.wheretogo.core.data.model.tour.LocationInfoDataList
import com.dogeby.wheretogo.core.data.model.tour.TourContentData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoMap
import com.dogeby.wheretogo.core.data.model.tour.toCommonInfoData
import com.dogeby.wheretogo.core.data.model.tour.toLocationInfoData
import com.dogeby.wheretogo.core.data.paging.FestivalInfoPagingSource
import com.dogeby.wheretogo.core.data.paging.KeywordSearchResultPagingSource
import com.dogeby.wheretogo.core.data.paging.TourInfoByRegionPagingSource
import com.dogeby.wheretogo.core.data.util.ContentTypeInfoLoader
import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManager
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.locationinfo.NetworkLocationInfoData
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CommonInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.LocationInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.TourInfoByRegionRequestBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant

@Singleton
class TourRepositoryImpl @Inject constructor(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val cachePreferencesManager: CachePreferencesManager,
) : TourRepository {

    private val contentTypeInfoLoader: ContentTypeInfoLoader =
        ContentTypeInfoLoader(tourNetworkDataSource)

    override fun getPagedTourInfoByRegion(
        currentPage: Int,
        numberOfRows: Int,
        contentTypeId: String,
        areaCode: String,
        sigunguCode: String,
        category1: String,
        category2: String,
        category3: String,
        arrangeOption: ArrangeOption,
    ): Flow<PagingData<TourContentData>> {
        return Pager(
            PagingConfig(numberOfRows),
        ) {
            TourInfoByRegionPagingSource(
                tourNetworkDataSource = tourNetworkDataSource,
                tourInfoByRegionRequestBody = TourInfoByRegionRequestBody(
                    currentPage = currentPage,
                    numberOfRows = numberOfRows,
                    contentTypeId = contentTypeId,
                    areaCode = areaCode,
                    sigunguCode = sigunguCode,
                    category1 = category1,
                    category2 = category2,
                    category3 = category3,
                ),
                arrangeOption = arrangeOption,
            )
        }.flow
    }

    override fun getPagedFestivalInfo(
        eventStartDate: Instant,
        currentPage: Int,
        numberOfRows: Int,
        arrangeOption: ArrangeOption,
    ): Flow<PagingData<FestivalData>> {
        return Pager(
            PagingConfig(numberOfRows),
        ) {
            FestivalInfoPagingSource(
                tourNetworkDataSource = tourNetworkDataSource,
                festivalInfoRequestBody = FestivalInfoRequestBody(
                    eventStartDate = eventStartDate,
                    currentPage = currentPage,
                    numberOfRows = numberOfRows,
                ),
                arrangeOption = arrangeOption,
            )
        }.flow
    }

    override fun searchKeyword(
        keyword: String,
        currentPage: Int,
        numberOfRows: Int,
        contentTypeId: String,
        areaCode: String,
        sigunguCode: String,
        category1: String,
        category2: String,
        category3: String,
        arrangeOption: ArrangeOption,
    ): Flow<PagingData<KeywordSearchResultData>> {
        return Pager(
            PagingConfig(numberOfRows),
        ) {
            KeywordSearchResultPagingSource(
                tourNetworkDataSource = tourNetworkDataSource,
                keywordSearchRequestBody = KeywordSearchRequestBody(
                    keyword = keyword,
                    currentPage = currentPage,
                    numberOfRows = numberOfRows,
                    contentTypeId = contentTypeId,
                    areaCode = areaCode,
                    sigunguCode = sigunguCode,
                    category1 = category1,
                    category2 = category2,
                    category3 = category3,
                ),
                arrangeOption = arrangeOption,
            )
        }.flow
    }

    override fun getCommonInfo(
        contentId: String,
        contentTypeId: String,
        isDefaultInfoIncluded: Boolean,
        isFirstImgIncluded: Boolean,
        isAreaCodeIncluded: Boolean,
        isCategoryIncluded: Boolean,
        isAddrInfoIncluded: Boolean,
        isMapInfoIncluded: Boolean,
        isOverviewIncluded: Boolean,
    ): Flow<Result<CommonInfoData>> {
        return flow {
            val result = try {
                val response = tourNetworkDataSource.fetchCommonInfo(
                    commonInfoRequestBody = CommonInfoRequestBody(
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
                ).getOrThrow()

                if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                    Result.failure(Exception(response.content.header.resultMessage))
                } else {
                    with(response.content.body) {
                        Result.success(result.items.first().toCommonInfoData())
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

            emit(result)
        }
    }

    override fun getLocationInfoList(areaCode: String): Flow<Result<List<LocationInfoData>>> {
        return flow {
            val result = try {
                val key = "$AREA_CODE_CACHE_KEY/$areaCode"
                val cachedLocationInfoDataList = cachePreferencesManager.loadValue(
                    key = key,
                    deserializer = LocationInfoDataList.serializer(),
                ).first()

                if (cachedLocationInfoDataList.isFailure) {
                    val response = tourNetworkDataSource.fetchLocationInfo(
                        LocationInfoRequestBody(
                            areaCode = areaCode,
                        ),
                    ).getOrThrow()

                    if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                        Result.failure(Exception(response.content.header.resultMessage))
                    } else {
                        val locationInfos = response.content.body.result
                            .items
                            .map(NetworkLocationInfoData::toLocationInfoData)
                        cachePreferencesManager.saveValue(
                            key = key,
                            serializer = LocationInfoDataList.serializer(),
                            value = LocationInfoDataList(locationInfos),
                        )
                        Result.success(locationInfos)
                    }
                } else {
                    Result.success(cachedLocationInfoDataList.getOrThrow().locationInfos)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

            emit(result)
        }
    }

    override fun getContentTypeInfoMap(): Flow<Result<Map<String, ContentTypeInfoData>>> {
        return flow {
            val result = try {
                val cachedContentTypeInfoMap = cachePreferencesManager.loadValue(
                    key = CONTENT_TYPE_CACHE_KEY,
                    deserializer = ContentTypeInfoMap.serializer(),
                ).first()
                Result.success(cachedContentTypeInfoMap.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result.map { it.contentTypeInfos })
        }
    }

    override fun fetchContentTypeInfoMap(): Flow<Result<Unit>> {
        return flow {
            val result = try {
                val cachedContentTypeInfoMap = cachePreferencesManager.loadValue(
                    key = CONTENT_TYPE_CACHE_KEY,
                    deserializer = ContentTypeInfoMap.serializer(),
                ).first()
                if (cachedContentTypeInfoMap.isFailure) {
                    val contentTypeInfoMap = contentTypeInfoLoader.fetchContentTypeInfoList()
                    cachePreferencesManager.saveValue(
                        key = CONTENT_TYPE_CACHE_KEY,
                        value = contentTypeInfoMap,
                        serializer = ContentTypeInfoMap.serializer(),
                    )
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
        const val AREA_CODE_CACHE_KEY = "AreaCodeCacheKey"
        const val CONTENT_TYPE_CACHE_KEY = "ContentTypeCacheKey"
    }
}
