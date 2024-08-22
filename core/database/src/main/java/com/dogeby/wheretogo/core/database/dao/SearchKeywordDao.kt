package com.dogeby.wheretogo.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dogeby.wheretogo.core.database.model.SearchKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceSearchKeyword(searchKeyword: SearchKeywordEntity)

    @Update
    suspend fun updateSearchKeyword(searchKeyword: SearchKeywordEntity)

    @Delete
    suspend fun deleteSearchKeyword(searchKeyword: SearchKeywordEntity)

    @Query(
        """
        DELETE FROM search_keywords
        WHERE keyword NOT IN (
            SELECT keyword FROM (
                SELECT keyword
                FROM search_keywords
                ORDER BY generationTime DESC
                LIMIT :limit
            ) AS recent_keywords
        )
    """,
    )
    suspend fun deleteOldestSearchKeywords(limit: Int = 10)

    @Query("SELECT * FROM search_keywords")
    fun getSearchKeywords(): Flow<List<SearchKeywordEntity>>

    @Query("SELECT * FROM search_keywords ORDER BY generationTime DESC")
    fun getRecentSearchKeywords(): Flow<List<SearchKeywordEntity>>
}
