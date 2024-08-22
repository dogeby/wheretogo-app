package com.dogeby.wheretogo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.dogeby.wheretogo.feature.contentdetail.navigation.contentDetailScreen
import com.dogeby.wheretogo.feature.contentdetail.navigation.navigateToContentDetail
import com.dogeby.wheretogo.feature.home.navigation.HOME_ROUTE
import com.dogeby.wheretogo.feature.home.navigation.homeScreen
import com.dogeby.wheretogo.feature.regionselection.navigation.navigateToRegionSelection
import com.dogeby.wheretogo.feature.regionselection.navigation.regionSelectionScreen
import com.dogeby.wheretogo.feature.search.navigation.SEARCH_ROUTE
import com.dogeby.wheretogo.feature.search.navigation.searchGraph
import com.dogeby.wheretogo.feature.searchresult.navigation.navigateToSearchResult
import com.dogeby.wheretogo.feature.searchresult.navigation.searchResultScreen

@Composable
fun WheretogoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            navigateToContents = { _, _, _ -> },
            navigateToFestivals = {},
            navigateToRegionSelection = {
                navController.navigateToRegionSelection(it)
            },
            navigateToContentDetail = {
                navController.navigateToContentDetail(it)
            },
        )
        contentDetailScreen()
        searchGraph(
            navigateToSearchResult = {
                navController.navigateToSearchResult(
                    searchKeyword = it,
                    navOptions = NavOptions.Builder().setPopUpTo(
                        route = SEARCH_ROUTE,
                        inclusive = true,
                    ).build(),
                )
            },
            onNavigateUp = { navController.popBackStack() },
        ) {
            searchResultScreen { navController.navigateToContentDetail(it) }
        }
        regionSelectionScreen(
            onNavigateToList = { _, _ -> },
        )
    }
}
