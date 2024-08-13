package com.dogeby.wheretogo.core.ui.components.bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.model.TopAppBarActionUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheretogoTopAppBar(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    actions: List<TopAppBarActionUiState> = emptyList(),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = titleRes))
        },
        modifier = modifier,
        actions = {
            actions.forEach {
                IconButton(onClick = it.onActionClick) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        modifier = modifier,
                    )
                }
            }
        },
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheretogoTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    navigationIconContentDescription: String? = null,
    actions: List<TopAppBarActionUiState> = emptyList(),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = titleRes))
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                )
            }
        },
        modifier = modifier,
        actions = {
            actions.forEach {
                IconButton(onClick = it.onActionClick) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        modifier = modifier,
                    )
                }
            }
        },
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun WheretogoTopAppBarPreview() {
    WheretogoTopAppBar(
        titleRes = android.R.string.untitled,
        actions = listOf(
            TopAppBarActionUiState(icon = Icons.Default.Search),
            TopAppBarActionUiState(icon = Icons.Outlined.Person),
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun WheretogoTopAppBarPreview_Navigation() {
    WheretogoTopAppBar(
        titleRes = android.R.string.untitled,
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onNavigationClick = {},
    )
}
