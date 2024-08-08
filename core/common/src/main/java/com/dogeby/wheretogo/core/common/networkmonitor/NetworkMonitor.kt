package com.dogeby.wheretogo.core.common.networkmonitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
