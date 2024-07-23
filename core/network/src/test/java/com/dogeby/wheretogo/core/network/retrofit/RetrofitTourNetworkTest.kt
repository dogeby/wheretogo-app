package com.dogeby.wheretogo.core.network.retrofit

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RetrofitTourNetworkTest {

    private lateinit var retrofitTourNetwork: RetrofitTourNetwork

    @Before
    fun setUp() {
        retrofitTourNetwork = RetrofitTourNetwork(Json { ignoreUnknownKeys = true })
    }

    @Test
    fun fetchTourInfoByRegion() = runTest {
        retrofitTourNetwork.fetchTourInfoByRegion()
            .onSuccess {
                Assert.assertEquals(
                    "0000",
                    it.response.header.resultCode,
                )
            }
            .onFailure {
                Assert.fail(it.message)
            }
    }
}
