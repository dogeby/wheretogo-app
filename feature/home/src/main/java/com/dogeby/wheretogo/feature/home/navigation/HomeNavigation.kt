package com.dogeby.wheretogo.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dogeby.wheretogo.core.model.tour.TourContentType
import com.dogeby.wheretogo.feature.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToContents: (contentTypeId: String, areaCode: String, sigunguCode: String) -> Unit,
    navigateToFestivals: () -> Unit,
    navigateToRegionSelection: (contentTypeId: String) -> Unit,
    navigateToContentDetail: (id: String) -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(
            navigateToList = { contentType, areaCode, sigunguCode ->
                when (contentType) {
                    TourContentType.Festival -> navigateToFestivals()
                    else -> {
                        if (areaCode == null) {
                            navigateToRegionSelection(contentType.id)
                        } else {
                            navigateToContents(
                                contentType.id,
                                areaCode,
                                sigunguCode ?: "",
                            )
                        }
                    }
                }
            },
            navigateToContentDetail = navigateToContentDetail,
        )
    }
}
