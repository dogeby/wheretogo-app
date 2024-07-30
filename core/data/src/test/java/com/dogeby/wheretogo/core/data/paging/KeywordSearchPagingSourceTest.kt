package com.dogeby.wheretogo.core.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.dogeby.wheretogo.core.data.model.tour.KeywordSearchData
import com.dogeby.wheretogo.core.data.model.tour.toKeywordSearchData
import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchData
import com.dogeby.wheretogo.core.network.model.tour.requestbody.KeywordSearchRequestBody
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class KeywordSearchPagingSourceTest {

    private lateinit var fakeTourNetworkDataSource: FakeTourNetworkDataSource

    @Before
    fun setUp() {
        fakeTourNetworkDataSource = FakeTourNetworkDataSource()
    }

    @Test
    fun test_keywordSearchPagingSource_success() = runTest {
        val requestBody = KeywordSearchRequestBody("cafe")
        val pager = createTestPager(requestBody)
        val expectedResult = PagingSource.LoadResult.Page(
            data = fakeTourNetworkDataSource
                .searchKeyword(requestBody)
                .getOrThrow()
                .content
                .body
                .result
                .items
                .map(NetworkKeywordSearchData::toKeywordSearchData),
            prevKey = null,
            nextKey = 2,
        )

        val actualResult = pager.refresh()

        Assert.assertEquals(
            expectedResult,
            actualResult,
        )
    }

    @Test
    fun test_keywordSearchPagingSource_error() = runTest {
        fakeTourNetworkDataSource.shouldReturnError = true
        val pager = createTestPager()

        val actualResult = pager.refresh()

        Assert.assertTrue(
            actualResult is PagingSource.LoadResult.Error,
        )
    }

    @Test
    fun test_keywordSearchPagingSource_consecutiveLoads() = runTest {
        val pager = createTestPager()
        val page = with(pager) {
            refresh()
            append()
            append()
        }

        if (page is PagingSource.LoadResult.Page) {
            Assert.assertTrue(page.nextKey == 4)
        } else {
            Assert.fail()
        }
    }

    @Test
    fun test_keywordSearchPagingSource_lastPage() = runTest {
        val numberOfRows = 12
        val requestBody = KeywordSearchRequestBody(
            keyword = "cafe",
            numberOfRows = numberOfRows,
        )
        val pager = createTestPager(requestBody)
        val page = pager.refresh(
            initialKey = (FakeTourNetworkDataSource.TOTAL_COUNT + numberOfRows - 1) / numberOfRows,
        )

        if (page is PagingSource.LoadResult.Page) {
            Assert.assertTrue(page.nextKey == null)
        } else {
            Assert.fail()
        }
    }

    private fun createTestPager(
        requestBody: KeywordSearchRequestBody = KeywordSearchRequestBody("cafe"),
    ): TestPager<Int, KeywordSearchData> {
        val pagingSource = KeywordSearchPagingSource(
            tourNetworkDataSource = fakeTourNetworkDataSource,
            keywordSearchRequestBody = requestBody,
        )
        return TestPager(
            PagingConfig(requestBody.numberOfRows),
            pagingSource,
        )
    }
}
