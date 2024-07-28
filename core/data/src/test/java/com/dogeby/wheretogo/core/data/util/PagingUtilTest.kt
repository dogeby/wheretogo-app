package com.dogeby.wheretogo.core.data.util

import org.junit.Assert.assertEquals
import org.junit.Test

class PagingUtilTest {

    @Test
    fun test_calculateNextKey_nextPageAvailable() {
        val currentPage = 1
        val numberOfRows = 10
        val totalCount = 25

        val actualNextKey = PagingUtil.calculateNextKey(currentPage, numberOfRows, totalCount)

        assertEquals(2, actualNextKey)
    }

    @Test
    fun test_calculateNextKey_lastPageReached() {
        val currentPage = 2
        val numberOfRows = 10
        val totalCount = 25

        val actualNextKey = PagingUtil.calculateNextKey(currentPage, numberOfRows, totalCount)

        assertEquals(3, actualNextKey)
    }

    @Test
    fun test_calculateNextKey_currentPageExceedsTotalPages() {
        val currentPage = 3
        val numberOfRows = 10
        val totalCount = 20

        val actualNextKey = PagingUtil.calculateNextKey(currentPage, numberOfRows, totalCount)

        assertEquals(null, actualNextKey)
    }
}
