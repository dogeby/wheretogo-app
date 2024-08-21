package com.dogeby.wheretogo.feature.searchresult.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.feature.searchresult.SearchResultRoute

internal const val SEARCH_KEYWORD_ARG = "search_keyword"
const val SEARCH_RESULT_ROUTE = "search_result_route/{$SEARCH_KEYWORD_ARG}"

internal class SearchResultArgs(val searchKeyword: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[SEARCH_KEYWORD_ARG])))
}

fun NavController.navigateToSearchResult(
    searchKeyword: String,
    navOptions: NavOptions? = null,
) {
    val encodedId = Uri.encode(searchKeyword)
    this.navigate("search_result_route/$encodedId", navOptions)
}

fun NavGraphBuilder.searchResultScreen(navigateToContentDetail: (id: String) -> Unit) {
    composable(
        route = SEARCH_RESULT_ROUTE,
        arguments = listOf(
            navArgument(SEARCH_KEYWORD_ARG) { type = NavType.StringType },
        ),
    ) {
        SearchResultRoute(navigateToContentDetail)
    }
}
