package com.dogeby.wheretogo.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogeby.wheretogo.core.database.dao.SearchKeywordDao
import com.dogeby.wheretogo.core.database.model.SearchKeywordEntity

@Database(
    entities = [
        SearchKeywordEntity::class,
    ],
    version = 1,
)
abstract class WheretogoDatabase : RoomDatabase() {

    abstract fun searchKeywordDao(): SearchKeywordDao
}
