package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource
import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource.Companion.CATEGORY_INFO_TOTAL_COUNT
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TourContentTypeInfoLoaderTest {

    private lateinit var contentTypeInfoLoader: ContentTypeInfoLoader

    @Before
    fun setUp() {
        val tourNetworkDataSource = FakeTourNetworkDataSource()

        contentTypeInfoLoader = ContentTypeInfoLoader(
            tourNetworkDataSource = tourNetworkDataSource,
        )
    }

    @Test
    fun test_getContentTypeInfoList_success() = runTest {
        val result = contentTypeInfoLoader.fetchContentTypeInfoList().contentTypeInfos

        assertEquals(TourContentType.entries.size, result.size)

        result.forEach { (key, contentType) ->
            assertEquals(key, contentType.contentType.id)
            assertEquals(CATEGORY_INFO_TOTAL_COUNT, contentType.majorCategories.size)

            contentType.majorCategories.forEach { (_, majorCategory) ->
                assertEquals(CATEGORY_INFO_TOTAL_COUNT, majorCategory.mediumCategories.size)

                majorCategory.mediumCategories.forEach { (_, mediumCategory) ->
                    assertEquals(CATEGORY_INFO_TOTAL_COUNT, mediumCategory.minorCategories.size)
                }
            }
        }
    }
}
