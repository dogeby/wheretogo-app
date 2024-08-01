package com.dogeby.wheretogo.core.datastore.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dogeby.wheretogo.core.datastore.exception.CacheExpiredException
import com.dogeby.wheretogo.core.datastore.model.cache.CacheEntry
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@Singleton
class CachePreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json,
) : CachePreferencesManager {

    override suspend fun <T> saveValue(
        key: String,
        serializer: KSerializer<T>,
        value: T,
        ttl: Long,
    ) {
        val entry = CacheEntry(
            data = value,
            expiresAt = System.currentTimeMillis() + ttl,
        )
        dataStore.edit { cache ->
            cache[stringPreferencesKey(key)] = json.encodeToString(
                serializer = CacheEntry.serializer(serializer),
                value = entry,
            )
        }
    }

    override suspend fun <T> loadValue(
        key: String,
        deserializer: KSerializer<T>,
    ): Flow<Result<T>> {
        return dataStore.data.map { cache ->
            val preference: String? = cache[stringPreferencesKey(key)]

            if (preference == null) {
                Result.failure(NullPointerException())
            } else {
                val entry = json.decodeFromString(
                    deserializer = CacheEntry.serializer(deserializer),
                    string = preference,
                )

                if (System.currentTimeMillis() > entry.expiresAt) {
                    Result.failure(CacheExpiredException())
                } else {
                    Result.success(entry.data)
                }
            }
        }
    }
}
