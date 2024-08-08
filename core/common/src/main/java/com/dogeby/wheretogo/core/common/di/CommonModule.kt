package com.dogeby.wheretogo.core.common.di

import com.dogeby.wheretogo.core.common.networkmonitor.NetworkMonitor
import com.dogeby.wheretogo.core.common.networkmonitor.NetworkMonitorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesNetworkMonitor(networkMonitor: NetworkMonitorImpl): NetworkMonitor = networkMonitor
}
