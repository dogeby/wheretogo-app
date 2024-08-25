package com.dogeby.wheretogo.feature.festivals.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dogeby.wheretogo.feature.festivals.FestivalsRoute

const val FESTIVALS_ROUTE = "festivals_route"

fun NavController.navigateToFestivals(navOptions: NavOptions? = null) {
    this.navigate(FESTIVALS_ROUTE, navOptions)
}

fun NavGraphBuilder.festivalsScreen(navigateToContentDetail: (id: String) -> Unit) {
    composable(route = FESTIVALS_ROUTE) {
        FestivalsRoute(navigateToContentDetail)
    }
}
