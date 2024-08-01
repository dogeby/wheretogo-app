package com.dogeby.wheretogo.core.datastore.cache

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.dogeby.wheretogo.core.common.di.CommonModule
import com.dogeby.wheretogo.core.datastore.di.DatastoreModule
import com.dogeby.wheretogo.core.datastore.exception.CacheExpiredException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CachePreferencesManagerTest {

    private lateinit var cachePreferencesManager: CachePreferencesManager

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataStore = DatastoreModule.providesCacheDataStore(context)
        val json = CommonModule.providesJson()

        cachePreferencesManager = CachePreferencesManagerImpl(dataStore, json)
    }

    @Test
    fun test_saveValueAndLoadValue_success() = runTest {
        val key = "key"
        val value = "test"

        cachePreferencesManager.saveValue(key, String.serializer(), value)
        val result = cachePreferencesManager
            .loadValue(key, String.serializer())
            .first()
            .getOrThrow()

        Assert.assertEquals(value, result)
    }

    @Test
    fun test_loadValue_expiredCache() = runTest {
        val key = "key_expired"
        val value = "test"
        val ttl = -1L

        cachePreferencesManager.saveValue(
            key = key,
            serializer = String.serializer(),
            value = value,
            ttl = ttl,
        )

        val expiredResult = cachePreferencesManager
            .loadValue(key, String.serializer())
            .first()

        Assert.assertTrue(expiredResult.isFailure)
        Assert.assertTrue(expiredResult.exceptionOrNull() is CacheExpiredException)
    }

    @Test
    fun test_loadValue_whenNoData() = runTest {
        val key = "nonExistentKey"

        val result = cachePreferencesManager
            .loadValue(key, String.serializer())
            .first()

        Assert.assertTrue(result.isFailure)
    }
}
