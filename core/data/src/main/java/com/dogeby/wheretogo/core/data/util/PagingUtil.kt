package com.dogeby.wheretogo.core.data.util

internal object PagingUtil {

    fun calculateNextKey(
        currentPage: Int,
        numberOfRows: Int,
        totalCount: Int,
    ): Int? {
        if (numberOfRows < 1) return null

        val totalPages = (totalCount + numberOfRows - 1) / numberOfRows

        return if (currentPage >= totalPages) {
            null
        } else {
            currentPage + 1
        }
    }
}
