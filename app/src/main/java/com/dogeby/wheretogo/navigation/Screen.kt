package com.dogeby.wheretogo.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.dogeby.wheretogo.R
import com.dogeby.wheretogo.core.ui.model.TopAppBarActionUiState

enum class Screen(
    val topAppBarState: ScreenTopAppBarState,
) {
    Home(
        topAppBarState = ScreenTopAppBarState(
            titleRes = R.string.app_name,
            actions = listOf(
                ScreenTopAppBarAction(
                    icon = Icons.Default.Search,
                ),
                ScreenTopAppBarAction(
                    icon = Icons.Default.Person,
                ),
            ),
        ),
    ),
}

data class ScreenTopAppBarState(
    @StringRes val titleRes: Int,
    val actions: List<ScreenTopAppBarAction>,
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
