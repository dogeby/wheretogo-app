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

const val CONTENT_DETAIL_ROUTE = "contentDetail_route"
internal const val CONTENT_ID_ARG = "content_id"

internal class ContentDetailArgs(val contentId: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[CONTENT_ID_ARG])))
}

fun NavController.navigateToContentDetail(
    contentId: String,
    navOptions: NavOptions? = null,
) {
    val encodedId = Uri.encode(contentId)
    this.navigate("$CONTENT_DETAIL_ROUTE/$encodedId", navOptions)
}

fun NavGraphBuilder.contentDetailScreen() {
    composable(
        route = "$CONTENT_DETAIL_ROUTE/{$CONTENT_ID_ARG}",
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
