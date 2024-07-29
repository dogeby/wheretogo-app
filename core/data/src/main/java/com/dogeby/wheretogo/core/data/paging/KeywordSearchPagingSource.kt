package com.dogeby.wheretogo.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchData
import com.dogeby.wheretogo.core.data.model.tour.toKeywordSearchData
import com.dogeby.wheretogo.core.data.util.PagingUtil.calculateNextKey
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchData

class KeywordSearchPagingSource(
    private val tourNetworkDataSource: TourNetworkDataSource,
    private val keywordSearchRequestBody: KeywordSearchRequestBody,
    private val arrangeOption: ArrangeOption = ArrangeOption.MODIFIED_TIME,
) : PagingSource<Int, KeywordSearchData>() {

    override fun getRefreshKey(state: PagingState<Int, KeywordSearchData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KeywordSearchData> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = tourNetworkDataSource
                .searchKeyword(
                    keywordSearchRequestBody = keywordSearchRequestBody.copy(
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
                        NetworkKeywordSearchData::toKeywordSearchData,
                    ),
                    prevKey = null,
                    nextKey = calculateNextKey(
                        currentPage = currentPage,
                        numberOfRows = keywordSearchRequestBody.numberOfRows,
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
