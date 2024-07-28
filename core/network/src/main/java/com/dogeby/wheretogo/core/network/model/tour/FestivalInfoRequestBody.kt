package com.dogeby.wheretogo.core.network.model.tour

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

data class FestivalInfoRequestBody(
    val eventStartDate: Instant = Clock.System.now(),
    val currentPage: Int = 1,
    val numberOfRows: Int = 12,
) {

    @OptIn(FormatStringsInDatetimeFormats::class)
    internal fun toQueryMap(): Map<String, String> {
        return mapOf(
            "pageNo" to currentPage.toString(),
            "numOfRows" to numberOfRows.toString(),
            "eventStartDate" to eventStartDate
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .format(
                    LocalDateTime.Format {
                        byUnicodePattern(DEFAULT_FORMAT_PATTERN)
                    },
                ),
        )
    }

    private companion object {
        const val DEFAULT_FORMAT_PATTERN = "yyyyMMdd"
    }
}
