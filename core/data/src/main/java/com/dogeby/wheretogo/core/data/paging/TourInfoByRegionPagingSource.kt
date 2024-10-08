package com.dogeby.wheretogo.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dogeby.wheretogo.core.data.model.tour.TourContentData
import com.dogeby.wheretogo.core.data.model.tour.toTourContentData
import com.dogeby.wheretogo.core.data.util.PagingUtil.calculateNextKey
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.requestbody.TourInfoByRegionRequestBody
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentData
import kotlin.Exception

class TourInfoByRegionPagingSource(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val tourInfoByRegionRequestBody: TourInfoByRegionRequestBody,
    private val arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
) : PagingSource<Int, TourContentData>() {

    override fun getRefreshKey(state: PagingState<Int, TourContentData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TourContentData> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = tourNetworkDataSource
                .fetchTourInfoByRegion(
                    tourInfoByRegionRequestBody = tourInfoByRegionRequestBody.copy(
                        currentPage = nextPageNumber,
                    ),
                    arrangeOption = arrangeOption,
                )
                .getOrThrow()

            if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                return LoadResult.Error(Exception(response.content.header.resultMessage))
            }
            with(response.content.body) {
                LoadResult.Page(
                    data = result.items.map(
                        NetworkTourContentData::toTourContentData,
                    ),
                    prevKey = null,
                    nextKey = calculateNextKey(
                        currentPage = currentPage,
                        numberOfRows = tourInfoByRegionRequestBody.numberOfRows,
                        totalCount = totalCount,
                    ),
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val SUCCESS_RESULT_CODE = "0000"
    }
}
