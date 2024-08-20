package com.dogeby.wheretogo.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dogeby.wheretogo.feature.search.SearchRoute

private const val SEARCH_GRAPH = "search_graph"
const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearchGraph(navOptions: NavOptions? = null) {
    this.navigate(SEARCH_GRAPH, navOptions)
}

fun NavGraphBuilder.searchGraph(
    navigateToSearchResult: (String) -> Unit,
    onNavigateUp: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = SEARCH_GRAPH,
        startDestination = SEARCH_ROUTE,
    ) {
        composable(route = SEARCH_ROUTE) {
            SearchRoute(
                navigateToSearchResult = navigateToSearchResult,
                onNavigateUp = onNavigateUp,
            )
        }
        nestedGraphs()
    }
}
