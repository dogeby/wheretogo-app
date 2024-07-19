package com.dogeby.wheretogo.core.ui.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(
    locale: Locale,
    inputPattern: String = "yyyyMMdd",
    outputPattern: String = "yy.M.d",
): String {
    val inputFormatter = SimpleDateFormat(inputPattern, locale)
    val outputFormatter = SimpleDateFormat(outputPattern, locale)

    return outputFormatter.format(inputFormatter.parse(this))
}

fun buildLocationText(
    areaName: String = "",
    sigunguName: String = "",
): String {
    return buildString {
        append(areaName)
        if (areaName.isNotBlank() && sigunguName.isNotBlank()) {
            append(" ")
        }
        append(sigunguName)
    }
}

fun buildLocationContentTypeText(
    areaName: String = "",
    sigunguName: String = "",
    contentTypeName: String = "",
): String {
    return buildString {
        append(buildLocationText(areaName, sigunguName))
        if (contentTypeName.isNotBlank()) {
            if (this.isNotBlank()) {
                append(" ")
            }
            append(contentTypeName)
        }
    }
}
