package com.dogeby.wheretogo.core.datastore.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface CachePreferencesManager {

    suspend fun <T> saveValue(
        key: String,
        serializer: KSerializer<T>,
        value: T,
        ttl: Long = DEFAULT_TIME_TO_LIVE,
    )

    suspend fun <T> loadValue(
        key: String,
        deserializer: KSerializer<T>,
    ): Flow<Result<T>>

    companion object {
        private const val DEFAULT_TIME_TO_LIVE = 1_000L * 60 * 60 * 24 * 14
    }
}
