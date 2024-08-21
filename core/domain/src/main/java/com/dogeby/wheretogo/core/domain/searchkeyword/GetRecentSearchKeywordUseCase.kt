package com.dogeby.wheretogo.core.domain.searchkeyword

import com.dogeby.wheretogo.core.data.repository.SearchKeywordRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetRecentSearchKeywordUseCase @Inject constructor(
    private val searchKeywordRepository: SearchKeywordRepository,
) {

    operator fun invoke(): Flow<List<String>> {
        return searchKeywordRepository.getRecentSearchKeywords()
    }
}
