package com.dogeby.wheretogo.core.domain.tour

import androidx.paging.PagingData
import androidx.paging.map
import com.dogeby.wheretogo.core.data.repository.SearchKeywordRepository
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.KeywordSearchResult
import com.dogeby.wheretogo.core.domain.model.tour.toKeywordSearchResult
import com.dogeby.wheretogo.core.domain.tour.areainfo.GetAreaInfoMapUseCase
import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SearchKeywordUseCase @Inject constructor(
    private val tourRepository: TourRepository,
    private val searchKeywordRepository: SearchKeywordRepository,
    private val getAreaInfoMapUseCase: GetAreaInfoMapUseCase,
    private val getContentTypeInfoMapUseCase: GetContentTypeInfoMapUseCase,
) {

    operator fun invoke(
        keyword: String,
        contentTypeId: String = "",
        areaCode: String = "",
        sigunguCode: String = "",
        majorCategoryCode: String = "",
        mediumCategoryCode: String = "",
        minorCategoryCode: String = "",
        arrangeOption: ArrangeOption = ArrangeOption.ModifiedTime,
    ): Flow<PagingData<KeywordSearchResult>> {
        return combine(
            getAreaInfoMapUseCase(),
            getContentTypeInfoMapUseCase(),
            tourRepository.searchKeyword(
                keyword = keyword,
                contentTypeId = contentTypeId,
                areaCode = areaCode,
                sigunguCode = sigunguCode,
                category1 = majorCategoryCode,
                category2 = mediumCategoryCode,
                category3 = minorCategoryCode,
                arrangeOption = arrangeOption,
            ),
        ) { areaInfoMapResult, contentTypeInfoMapResult, pagedSearchKeywordResultData ->
            try {
                val areaInfoMap = areaInfoMapResult.getOrThrow()
                val contentTypeInfoMap = contentTypeInfoMapResult.getOrThrow()

                searchKeywordRepository.addSearchKeyword(keyword)
                searchKeywordRepository.deleteOldestSearchKeywords()

                pagedSearchKeywordResultData.map { keywordSearchResultData ->
                    keywordSearchResultData.toKeywordSearchResult(
                        contentTypeInfoMap = contentTypeInfoMap,
                        areaInfoMap = areaInfoMap,
                    )
                }
            } catch (e: Exception) {
                PagingData.empty()
            }
        }
    }
}
