package com.dogeby.wheretogo.core.data.repository

import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.tour.CommonInfoData
import com.dogeby.wheretogo.core.data.model.tour.FestivalData
import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchResultData
import com.dogeby.wheretogo.core.data.model.tour.TourContentData
import com.dogeby.wheretogo.core.data.model.tour.locationinfo.AreaInfoData
import com.dogeby.wheretogo.core.data.model.tour.serviceinfo.ContentTypeInfoData
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface TourRepository {

    fun getPagedTourInfoByRegion(
        currentPage: Int = 1,
        numberOfRows: Int = 12,
        contentTypeId: String = "",
        areaCode: String = "",
        sigunguCode: String = "",
        category1: String = "",
        category2: String = "",
        category3: String = "",
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<PagingData<TourContentData>>

    fun getPagedFestivalInfo(
        eventStartDate: Instant = Clock.System.now(),
        currentPage: Int = 1,
        numberOfRows: Int = 12,
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<PagingData<FestivalData>>

    fun searchKeyword(
        keyword: String,
        currentPage: Int = 1,
        numberOfRows: Int = 12,
        contentTypeId: String = "",
        areaCode: String = "",
        sigunguCode: String = "",
        category1: String = "",
        category2: String = "",
        category3: String = "",
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<PagingData<KeywordSearchResultData>>

    fun getCommonInfo(
        contentId: String,
        contentTypeId: String = "",
        isDefaultInfoIncluded: Boolean = true,
        isFirstImgIncluded: Boolean = true,
        isAreaCodeIncluded: Boolean = true,
        isCategoryIncluded: Boolean = true,
        isAddrInfoIncluded: Boolean = true,
        isMapInfoIncluded: Boolean = true,
        isOverviewIncluded: Boolean = true,
    ): Flow<Result<CommonInfoData>>

    fun getAreaInfoMap(): Flow<Result<Map<String, AreaInfoData>>>

    fun fetchAreaInfoMap(): Flow<Result<Unit>>

    fun getContentTypeInfoMap(): Flow<Result<Map<String, ContentTypeInfoData>>>

    fun fetchContentTypeInfoMap(): Flow<Result<Unit>>
}
