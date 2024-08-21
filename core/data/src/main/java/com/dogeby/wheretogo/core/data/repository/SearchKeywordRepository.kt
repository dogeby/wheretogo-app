package com.dogeby.wheretogo.core.data.repository

import kotlinx.coroutines.flow.Flow

interface SearchKeywordRepository {

    suspend fun addSearchKeyword(keyword: String)

    suspend fun deleteSearchKeyword(keyword: String)

    suspend fun deleteOldestSearchKeywords(limit: Int = 10)

    fun getSearchKeywords(): Flow<List<String>>

    fun getRecentSearchKeywords(): Flow<List<String>>
}
