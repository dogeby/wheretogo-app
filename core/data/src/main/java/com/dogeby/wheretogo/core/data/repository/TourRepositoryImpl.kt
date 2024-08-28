package com.dogeby.wheretogo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.tour.CommonInfoData
import com.dogeby.wheretogo.core.data.model.tour.FestivalData
import com.dogeby.wheretogo.core.data.model.tour.ImgInfoData
import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchResultData
import com.dogeby.wheretogo.core.data.model.tour.TourContentData
import com.dogeby.wheretogo.core.data.model.tour.locationinfo.AreaInfoData
import com.dogeby.wheretogo.core.data.model.tour.locationinfo.AreaInfoMap
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoMap
import com.dogeby.wheretogo.core.data.model.tour.toCommonInfoData
import com.dogeby.wheretogo.core.data.paging.FestivalInfoPagingSource
import com.dogeby.wheretogo.core.data.paging.ImgInfoPagingSource
import com.dogeby.wheretogo.core.data.paging.KeywordSearchResultPagingSource
import com.dogeby.wheretogo.core.data.paging.TourInfoByRegionPagingSource
import com.dogeby.wheretogo.core.data.util.AreaInfoLoader
import com.dogeby.wheretogo.core.data.util.ContentTypeInfoLoader
import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManager
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.requestbody.CommonInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.ImgInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.requestbody.TourInfoByRegionRequestBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.Instant

@Singleton
class TourRepositoryImpl @Inject constructor(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val cachePreferencesManager: CachePreferencesManager,
) : TourRepository {

    private val contentTypeInfoLoader: ContentTypeInfoLoader =
        ContentTypeInfoLoader(tourNetworkDataSource)
    private val areaInfoLoader: AreaInfoLoader =
        AreaInfoLoader(tourNetworkDataSource)

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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAreaInfoMap(): Flow<Result<Map<String, AreaInfoData>>> {
        return cachePreferencesManager.loadValue(
            key = AREA_CODE_CACHE_KEY,
            deserializer = AreaInfoMap.serializer(),
        ).mapLatest { result ->
            result.map { it.areaInfos }
        }
    }

    override fun fetchAreaInfoMap(): Flow<Result<Unit>> {
        return flow {
            try {
                val cachedAreaInfoMap = cachePreferencesManager.loadValue(
                    key = AREA_CODE_CACHE_KEY,
                    deserializer = AreaInfoMap.serializer(),
                ).first()

                if (cachedAreaInfoMap.isFailure) {
                    val areaInfoMap = areaInfoLoader.fetchAreaInfoMap()
                    cachePreferencesManager.saveValue(
                        key = AREA_CODE_CACHE_KEY,
                        serializer = AreaInfoMap.serializer(),
                        value = areaInfoMap,
                    )
                }
                emit(Result.success(Unit))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getContentTypeInfoMap(): Flow<Result<Map<String, ContentTypeInfoData>>> {
        return cachePreferencesManager.loadValue(
            key = CONTENT_TYPE_CACHE_KEY,
            deserializer = ContentTypeInfoMap.serializer(),
        ).mapLatest { result ->
            result.map { it.contentTypeInfos }
        }
    }

    override fun fetchContentTypeInfoMap(): Flow<Result<Unit>> {
        return flow {
            try {
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
                emit(Result.success(Unit))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override fun getPagedImgInfo(
        contentId: String,
        currentPage: Int,
        numberOfRows: Int,
    ): Flow<PagingData<ImgInfoData>> {
        return Pager(
            PagingConfig(numberOfRows),
        ) {
            ImgInfoPagingSource(
                tourNetworkDataSource = tourNetworkDataSource,
                imgInfoRequestBody = ImgInfoRequestBody(
                    contentId = contentId,
                    currentPage = currentPage,
                    numberOfRows = numberOfRows,
                ),
            )
        }.flow
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
        const val AREA_CODE_CACHE_KEY = "AreaCodeCacheKey"
        const val CONTENT_TYPE_CACHE_KEY = "ContentTypeCacheKey"
    }
}
