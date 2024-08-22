package com.dogeby.wheretogo.core.data.repository

import com.dogeby.wheretogo.core.database.dao.SearchKeywordDao
import com.dogeby.wheretogo.core.database.model.SearchKeywordEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class SearchKeywordRepositoryImpl @Inject constructor(
    private val searchKeywordDao: SearchKeywordDao,
) : SearchKeywordRepository {

    override suspend fun addSearchKeyword(keyword: String) {
        searchKeywordDao.insertOrReplaceSearchKeyword(
            SearchKeywordEntity(keyword),
        )
    }

    override suspend fun deleteSearchKeyword(keyword: String) {
        searchKeywordDao.deleteSearchKeyword(SearchKeywordEntity(keyword))
    }

    override suspend fun deleteOldestSearchKeywords(limit: Int) {
        searchKeywordDao.deleteOldestSearchKeywords(limit)
    }

    override fun getSearchKeywords(): Flow<List<String>> {
        return searchKeywordDao.getSearchKeywords().map { searchKeywords ->
            searchKeywords.map { it.keyword }
        }
    }

    override fun getRecentSearchKeywords(): Flow<List<String>> {
        return searchKeywordDao.getRecentSearchKeywords().map { searchKeywords ->
            searchKeywords.map { it.keyword }
        }
    }
}
