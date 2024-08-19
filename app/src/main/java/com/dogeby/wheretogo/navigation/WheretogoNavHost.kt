package com.dogeby.wheretogo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dogeby.wheretogo.feature.contentdetail.navigation.contentDetailScreen
import com.dogeby.wheretogo.feature.contentdetail.navigation.navigateToContentDetail
import com.dogeby.wheretogo.feature.home.navigation.HOME_ROUTE
import com.dogeby.wheretogo.feature.home.navigation.homeScreen

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
            navigateToContentDetail = {
                navController.navigateToContentDetail(it)
            },
        )
        contentDetailScreen()
    }
}
