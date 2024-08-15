package com.dogeby.wheretogo.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogeby.wheretogo.R
import com.dogeby.wheretogo.core.common.networkmonitor.NetworkMonitor
import com.dogeby.wheretogo.core.ui.components.bar.WheretogoTopAppBar
import com.dogeby.wheretogo.navigation.WheretogoNavHost
import com.dogeby.wheretogo.navigation.toTopAppBarActionUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheretogoApp(
    networkMonitor: NetworkMonitor,
    appState: WheretogoAppState = rememberWheretogoAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val topAppBarState = appState.currentScreen?.topAppBarState
            if (topAppBarState != null) {
                WheretogoTopAppBar(
                    titleRes = topAppBarState.titleRes,
                    navigation = topAppBarState.navigation
                        ?.toTopAppBarActionUiState(appState.navController),
                    actions = topAppBarState.actions.map {
                        it.toTopAppBarActionUiState(appState.navController)
                    },
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { contentPadding ->
        val isOffline by appState.isOffline.collectAsStateWithLifecycle()
        val notConnected = stringResource(id = R.string.not_connected)

        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnected,
                    duration = SnackbarDuration.Indefinite,
                )
            }
        }

        WheretogoNavHost(
            navController = appState.navController,
            modifier = Modifier
                .padding(contentPadding)
                .consumeWindowInsets(contentPadding),
        )
    }
}
