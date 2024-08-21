package com.dogeby.wheretogo.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.dogeby.wheretogo.R
import com.dogeby.wheretogo.core.ui.model.TopAppBarActionUiState
import com.dogeby.wheretogo.feature.search.navigation.navigateToSearchGraph

enum class Screen(
    val topAppBarState: ScreenTopAppBarState,
) {
    Home(
        topAppBarState = ScreenTopAppBarState(
            titleRes = R.string.app_name,
            actions = listOf(
                ScreenTopAppBarAction(
                    icon = Icons.Default.Search,
                    onActionClick = { it.navigateToSearchGraph() },
                ),
                ScreenTopAppBarAction(
                    icon = Icons.Outlined.Person,
                ),
            ),
        ),
    ),
    ContentDetail(
        topAppBarState = ScreenTopAppBarState(
            titleRes = null,
            navigation = ScreenTopAppBarAction(
                icon = Icons.AutoMirrored.Default.ArrowBack,
                onActionClick = { it.popBackStack() },
            ),
        ),
    ),
    SearchResult(
        topAppBarState = ScreenTopAppBarState(
            titleRes = R.string.search_result,
            navigation = ScreenTopAppBarAction(
                icon = Icons.AutoMirrored.Default.ArrowBack,
                onActionClick = { it.popBackStack() },
            ),
        ),
    ),
}

data class ScreenTopAppBarState(
    @StringRes val titleRes: Int?,
    val navigation: ScreenTopAppBarAction? = null,
    val actions: List<ScreenTopAppBarAction> = emptyList(),
)

data class ScreenTopAppBarAction(
    val icon: ImageVector,
    val contentDescription: String? = null,
    val onActionClick: (NavHostController) -> Unit = {},
)

internal fun ScreenTopAppBarAction.toTopAppBarActionUiState(navHostController: NavHostController) =
    TopAppBarActionUiState(
        icon = icon,
        contentDescription = contentDescription,
        onActionClick = { onActionClick(navHostController) },
    )
