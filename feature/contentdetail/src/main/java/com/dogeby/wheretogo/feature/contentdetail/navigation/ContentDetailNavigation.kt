package com.dogeby.wheretogo.feature.contentdetail.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.feature.contentdetail.ContentDetailRoute

internal const val CONTENT_ID_ARG = "content_id"
const val CONTENT_DETAIL_ROUTE = "contentDetail_route/{$CONTENT_ID_ARG}"

internal class ContentDetailArgs(val contentId: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[CONTENT_ID_ARG])))
}

fun NavController.navigateToContentDetail(
    contentId: String,
    navOptions: NavOptions? = null,
) {
    val encodedId = Uri.encode(contentId)
    this.navigate("contentDetail_route/$encodedId", navOptions)
}

fun NavGraphBuilder.contentDetailScreen() {
    composable(
        route = CONTENT_DETAIL_ROUTE,
        arguments = listOf(
            navArgument(CONTENT_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        ContentDetailRoute(
            navigateToReviewCreate = {},
            navigateToReviewEdit = {},
        )
    }
}
