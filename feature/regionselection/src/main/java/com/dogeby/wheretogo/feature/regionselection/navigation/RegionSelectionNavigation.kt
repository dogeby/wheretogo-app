package com.dogeby.wheretogo.feature.regionselection.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.feature.regionselection.RegionSelectionRoute

internal const val REGION_SELECTION_CONTENT_TYPE_ID_ARG = "region_selection_content_type_id"
const val REGION_SELECTION_ROUTE = "region_selection_route/{$REGION_SELECTION_CONTENT_TYPE_ID_ARG}"

internal class RegionSelectionArgs(val contentTypeId: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(
            stringDecoder.decodeString(
                checkNotNull(savedStateHandle[REGION_SELECTION_CONTENT_TYPE_ID_ARG]),
            ),
        )
}

fun NavController.navigateToRegionSelection(
    contentTypeId: String,
    navOptions: NavOptions? = null,
) {
    val encodedId = Uri.encode(contentTypeId)
    this.navigate("region_selection_route/$encodedId", navOptions)
}

fun NavGraphBuilder.regionSelectionScreen(
    onNavigateToList: (contentTypeId: String, areaCode: String) -> Unit,
) {
    composable(
        route = REGION_SELECTION_ROUTE,
        arguments = listOf(
            navArgument(REGION_SELECTION_CONTENT_TYPE_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        RegionSelectionRoute(onNavigateToList)
    }
}
