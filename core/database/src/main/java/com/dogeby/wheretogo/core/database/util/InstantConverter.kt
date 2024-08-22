package com.dogeby.wheretogo.core.database.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InstantConverter {

    @TypeConverter
    fun instantToString(instant: Instant) = Json.encodeToString(instant)

    @TypeConverter
    fun stringToInstant(json: String) = Json.decodeFromString<Instant>(json)
}
