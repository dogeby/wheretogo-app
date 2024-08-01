package com.dogeby.wheretogo.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManager
import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    private val Context.cacheDataStore by preferencesDataStore("cache")

    @Provides
    @Singleton
    fun providesCacheDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.cacheDataStore
    }

    @Provides
    @Singleton
    fun providesCachePreferencesManager(
        cachePreferencesManagerImpl: CachePreferencesManagerImpl,
    ): CachePreferencesManager = cachePreferencesManagerImpl
}
