package com.dogeby.wheretogo.core.datastore.fake

import com.dogeby.wheretogo.core.datastore.cache.CachePreferencesManager
import com.dogeby.wheretogo.core.datastore.exception.CacheExpiredException
import com.dogeby.wheretogo.core.datastore.model.cache.CacheEntry
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeCachePreferencesManager @Inject constructor(
    private val json: Json,
) : CachePreferencesManager {

    private val cache = mutableMapOf<String, String>()

    override suspend fun <T> saveValue(
        key: String,
        serializer: KSerializer<T>,
        value: T,
        ttl: Long,
    ): Result<Unit> = runCatching {
        val entry = CacheEntry(
            data = value,
            expiresAt = System.currentTimeMillis() + ttl,
        )
        cache[key] = json.encodeToString(
            serializer = CacheEntry.serializer(serializer),
            value = entry,
        )
    }

    override fun <T> loadValue(
        key: String,
        deserializer: KSerializer<T>,
    ): Flow<Result<T>> {
        val preference: String? = cache[key]

        return flow {
            val result = if (preference == null) {
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
            emit(result)
        }
    }
}
