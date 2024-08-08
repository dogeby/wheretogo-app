package com.dogeby.wheretogo

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dogeby.wheretogo.feature.home.navigation.HOME_ROUTE
import com.dogeby.wheretogo.feature.home.navigation.homeScreen

@Composable
fun WheretogoNavHost(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit,
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
            navigateToContentDetail = {},
            nestedGraphs = {},
        )
    }
}
