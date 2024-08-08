package com.dogeby.wheretogo.core.common.networkmonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

class NetworkMonitorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkMonitor {

    private val availableNetwork: MutableSet<String> = mutableSetOf()

    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()

        val callback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                synchronized(availableNetwork) {
                    availableNetwork.add(network.toString())
                    channel.trySend(availableNetwork.isNotEmpty())
                }
            }

            override fun onLost(network: Network) {
                synchronized(availableNetwork) {
                    availableNetwork.remove(network.toString())
                    channel.trySend(availableNetwork.isNotEmpty())
                }
            }
        }

        connectivityManager?.registerNetworkCallback(
            Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback,
        )

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }
        .conflate()
}
