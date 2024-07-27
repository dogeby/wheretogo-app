package com.dogeby.wheretogo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.tour.festival.FestivalData
import com.dogeby.wheretogo.core.data.model.tour.tourcontent.TourContentData
import com.dogeby.wheretogo.core.data.paging.FestivalInfoPagingSource
import com.dogeby.wheretogo.core.data.paging.TourInfoByRegionPagingSource
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.TourInfoByRegionRequestBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
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
}
