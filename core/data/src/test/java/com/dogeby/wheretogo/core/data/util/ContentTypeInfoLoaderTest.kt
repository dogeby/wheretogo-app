package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource
import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource.Companion.SERVICE_INFO_TOTAL_COUNT
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ContentTypeInfoLoaderTest {

    private lateinit var contentTypeInfoLoader: ContentTypeInfoLoader

    @Before
    fun setUp() {
        contentTypeInfoLoader = ContentTypeInfoLoader(FakeTourNetworkDataSource())
    }

    @Test
    fun test_getContentTypeInfoList_success() = runTest {
        val result = contentTypeInfoLoader.getContentTypeInfoList()
        println(result)

        assertEquals(2, result.size)

        result.forEach { (key, contentType) ->
            assertEquals("${key.last()}", contentType.code)
            assertEquals(SERVICE_INFO_TOTAL_COUNT, contentType.majorCategories.size)

            contentType.majorCategories.forEach { (_, majorCategory) ->
                assertEquals(SERVICE_INFO_TOTAL_COUNT, majorCategory.mediumCategories.size)

                majorCategory.mediumCategories.forEach { (_, mediumCategory) ->
                    assertEquals(SERVICE_INFO_TOTAL_COUNT, mediumCategory.minorCategories.size)
                }
            }
        }
    }
}
