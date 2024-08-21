package com.dogeby.wheretogo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dogeby.wheretogo.core.common.networkmonitor.NetworkMonitor
import com.dogeby.wheretogo.feature.contentdetail.navigation.CONTENT_DETAIL_ROUTE
import com.dogeby.wheretogo.feature.home.navigation.HOME_ROUTE
import com.dogeby.wheretogo.feature.searchresult.navigation.SEARCH_RESULT_ROUTE
import com.dogeby.wheretogo.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberWheretogoAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
): WheretogoAppState {
    return remember(navController, coroutineScope, networkMonitor) {
        WheretogoAppState(navController, coroutineScope, networkMonitor)
    }
}

@Stable
class WheretogoAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentScreen: Screen?
        @Composable
        get() = when (currentDestination?.route) {
            HOME_ROUTE -> Screen.Home
            CONTENT_DETAIL_ROUTE -> Screen.ContentDetail
            SEARCH_RESULT_ROUTE -> Screen.SearchResult
            else -> null
        }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}
