package com.dogeby.wheretogo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.dogeby.wheretogo.core.common.networkmonitor.NetworkMonitor

@Composable
fun WheretogoApp(
    networkMonitor: NetworkMonitor,
    appState: WheretogoAppState = rememberWheretogoAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
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
            snackbarHostState = snackbarHostState,
            onNavigateUp = { appState.onNavigateUp() },
            modifier = Modifier.padding(contentPadding),
        )
    }
}
