package com.dogeby.wheretogo.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dogeby.wheretogo.core.data.model.tour.ImgInfoData
import com.dogeby.wheretogo.core.data.model.tour.toImgInfoData
import com.dogeby.wheretogo.core.data.util.PagingUtil.calculateNextKey
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.imginfo.NetworkImgInfoData
import com.dogeby.wheretogo.core.network.model.tour.requestbody.ImgInfoRequestBody

class ImgInfoPagingSource(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val imgInfoRequestBody: ImgInfoRequestBody,
) : PagingSource<Int, ImgInfoData>() {

    override fun getRefreshKey(state: PagingState<Int, ImgInfoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImgInfoData> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = tourNetworkDataSource
                .fetchImgInfo(
                    imgInfoRequestBody = imgInfoRequestBody.copy(
                        currentPage = nextPageNumber,
                    ),
                )
                .getOrThrow()

            if (response.content.header.resultCode != SUCCESS_RESULT_CODE) {
                return LoadResult.Error(Exception(response.content.header.resultMessage))
            }
            with(response.content.body) {
                LoadResult.Page(
                    data = result.items.map(
                        NetworkImgInfoData::toImgInfoData,
                    ),
                    prevKey = null,
                    nextKey = calculateNextKey(
                        currentPage = currentPage,
                        numberOfRows = imgInfoRequestBody.numberOfRows,
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
