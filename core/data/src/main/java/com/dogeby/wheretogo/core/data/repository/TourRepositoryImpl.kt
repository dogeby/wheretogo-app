package com.dogeby.wheretogo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.tour.CommonInfoData
import com.dogeby.wheretogo.core.data.model.tour.FestivalData
import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchData
import com.dogeby.wheretogo.core.data.model.tour.LocationInfoData
import com.dogeby.wheretogo.core.data.model.tour.TourContentData
import com.dogeby.wheretogo.core.data.model.tour.toCommonInfoData
import com.dogeby.wheretogo.core.data.model.tour.toLocationInfoData
import com.dogeby.wheretogo.core.data.paging.FestivalInfoPagingSource
import com.dogeby.wheretogo.core.data.paging.KeywordSearchPagingSource
import com.dogeby.wheretogo.core.data.paging.TourInfoByRegionPagingSource
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
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant

@Singleton
class TourRepositoryImpl @Inject constructor(
    private val tourNetworkDataSource: TourNetworkDataSource,
) : TourRepository {

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
    ): Flow<PagingData<KeywordSearchData>> {
        return Pager(
            PagingConfig(numberOfRows),
        ) {
            KeywordSearchPagingSource(
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

            val result = try {
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
            val response = tourNetworkDataSource.fetchLocationInfo(
                LocationInfoRequestBody(
                    areaCode = areaCode,
                ),
            ).getOrThrow()

            val result = try {
                if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                    Result.failure(Exception(response.content.header.resultMessage))
                } else {
                    with(response.content.body) {
                        Result.success(
                            result
                                .items
                                .map(NetworkLocationInfoData::toLocationInfoData),
                        )
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

            emit(result)
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
    }
}
