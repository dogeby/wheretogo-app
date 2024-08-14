package com.dogeby.wheretogo.core.data.util

import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource
import com.dogeby.wheretogo.core.network.fake.FakeTourNetworkDataSource.Companion.LOCATION_INFO_TOTAL_COUNT
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AreaInfoLoaderTest {

    private lateinit var areaInfoLoader: AreaInfoLoader

    @Before
    fun setUp() {
        val tourNetworkDataSource = FakeTourNetworkDataSource()
        areaInfoLoader = AreaInfoLoader(tourNetworkDataSource)
    }

    @Test
    fun test_fetchAreaInfoMap_success() = runTest {
        val result = areaInfoLoader.fetchAreaInfoMap().areaInfos

        Assert.assertEquals(
            LOCATION_INFO_TOTAL_COUNT,
            result.values.size,
        )

        result.forEach { (key, areaInfo) ->
            Assert.assertEquals(key, areaInfo.locationInfo.code)
            Assert.assertEquals(LOCATION_INFO_TOTAL_COUNT, areaInfo.sigunguInfos.size)
        }
    }
}
