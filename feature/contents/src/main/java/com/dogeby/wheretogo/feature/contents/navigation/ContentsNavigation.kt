package com.dogeby.wheretogo.feature.contents.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dogeby.wheretogo.core.common.decoder.StringDecoder
import com.dogeby.wheretogo.feature.contents.ContentsRoute

internal const val CONTENTS_CONTENT_TYPE_ID_ARG = "contents_content_type_id"
internal const val CONTENTS_AREA_CODE_ARG = "contents_area_code"
const val CONTENTS_ROUTE =
    "contents_route/{$CONTENTS_CONTENT_TYPE_ID_ARG}/{$CONTENTS_AREA_CODE_ARG}"

private const val AREA_CODE_ARG_BLANK = "blank"

internal class ContentsArgs(
    val contentTypeId: String,
    val areaCode: String,
) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(
            stringDecoder.decodeString(
                checkNotNull(savedStateHandle[CONTENTS_CONTENT_TYPE_ID_ARG]),
            ),
            stringDecoder.decodeString(
                checkNotNull(savedStateHandle[CONTENTS_AREA_CODE_ARG]),
            ).run {
                if (this == AREA_CODE_ARG_BLANK) "" else this
            },
        )
}

fun NavController.navigateToContents(
    contentTypeId: String,
    areaCode: String = "",
    navOptions: NavOptions? = null,
) {
    val encodedContentTypeId = Uri.encode(contentTypeId)
    val encodedAreaCode = Uri.encode(areaCode.ifBlank { AREA_CODE_ARG_BLANK })

    this.navigate("contents_route/$encodedContentTypeId/$encodedAreaCode", navOptions)
}

fun NavGraphBuilder.contentsScreen(navigateToContentDetail: (id: String) -> Unit) {
    composable(
        route = CONTENTS_ROUTE,
        arguments = listOf(
            navArgument(CONTENTS_CONTENT_TYPE_ID_ARG) { type = NavType.StringType },
            navArgument(CONTENTS_AREA_CODE_ARG) { type = NavType.StringType },
        ),
    ) {
        ContentsRoute(navigateToContentDetail = navigateToContentDetail)
    }
}
