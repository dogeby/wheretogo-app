package com.dogeby.wheretogo.core.data.repository

import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.tour.festival.FestivalData
import com.dogeby.wheretogo.core.data.model.tour.tourcontent.TourContentData
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface TourRepository {

    fun getPagedTourInfoByRegion(
        currentPage: Int,
        numberOfRows: Int = 12,
        contentTypeId: String = "",
        areaCode: String = "",
        sigunguCode: String = "",
        category1: String = "",
        category2: String = "",
        category3: String = "",
        arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
    ): Flow<PagingData<TourContentData>>

    fun getPagedFestivalInfo(
        eventStartDate: Instant = Clock.System.now(),
        currentPage: Int = 1,
        numberOfRows: Int = 12,
        arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
    ): Flow<PagingData<FestivalData>>
}
