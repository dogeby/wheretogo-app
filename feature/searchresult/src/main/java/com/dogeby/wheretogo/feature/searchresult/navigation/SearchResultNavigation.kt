package com.dogeby.wheretogo.feature.searchresult.navigation

import androidx.lifecycle.SavedStateHandle
import com.dogeby.wheretogo.core.common.decoder.StringDecoder

internal const val SEARCH_KEYWORD_ARG = "search_keyword"
const val SEARCH_RESULT_ROUTE = "search_result_route/{$SEARCH_KEYWORD_ARG}"

internal class SearchResultArgs(val searchKeyword: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[SEARCH_KEYWORD_ARG])))
}
