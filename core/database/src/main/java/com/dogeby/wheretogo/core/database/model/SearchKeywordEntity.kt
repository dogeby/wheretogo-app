package com.dogeby.wheretogo.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.wheretogo.core.database.util.InstantConverter
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Entity(tableName = "search_keywords")
@TypeConverters(InstantConverter::class)
data class SearchKeywordEntity(
    @PrimaryKey val keyword: String,
    val generationTime: Instant = Clock.System.now(),
)
