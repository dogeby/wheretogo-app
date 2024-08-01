package com.dogeby.wheretogo.core.datastore.model.cache

import kotlinx.serialization.Serializable

@Serializable
data class CacheEntry<T>(
    val data: T,
    val expiresAt: Long,
)
