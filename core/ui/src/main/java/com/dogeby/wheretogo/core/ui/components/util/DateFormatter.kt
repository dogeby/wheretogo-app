package com.dogeby.wheretogo.core.ui.components.util

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
