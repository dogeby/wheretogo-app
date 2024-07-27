package com.dogeby.wheretogo.core.network.retrofit

import com.dogeby.wheretogo.core.network.model.tour.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.TourInfoByRegionRequestBody
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
    fun test_fetchTourInfoByRegion_success() = runTest {
        retrofitTourNetwork.fetchTourInfoByRegion(TourInfoByRegionRequestBody())
            .onSuccess {
                Assert.assertEquals(
                    "0000",
                    it.content.header.resultCode,
                )
            }
            .onFailure {
                Assert.fail(it.message)
            }
    }

    @Test
    fun test_fetchFestivalInfo_success() = runTest {
        retrofitTourNetwork.fetchFestivalInfo(FestivalInfoRequestBody())
            .onSuccess {
                println(it)
                Assert.assertEquals(
                    "0000",
                    it.content.header.resultCode,
                )
            }
            .onFailure {
                Assert.fail(it.message)
            }
    }
}
