package com.dogeby.wheretogo.core.data.repository

import androidx.paging.PagingData
import com.dogeby.wheretogo.core.data.model.TourContentData
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import kotlinx.coroutines.flow.Flow

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
        arrangeOption: ArrangeOption = ArrangeOption.TITLE,
    ): Flow<PagingData<TourContentData>>
}
