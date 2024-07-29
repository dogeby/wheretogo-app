package com.dogeby.wheretogo.core.network.retrofit

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.BuildConfig
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.FestivalInfoRequestBody
import com.dogeby.wheretogo.core.network.model.tour.KeywordSearchRequestBody
import com.dogeby.wheretogo.core.network.model.tour.TourInfoByRegionRequestBody
import com.dogeby.wheretogo.core.network.model.tour.festival.NetworkFestivalResponse
import com.dogeby.wheretogo.core.network.model.tour.keywordsearch.NetworkKeywordSearchResponse
import com.dogeby.wheretogo.core.network.model.tour.tourcontent.NetworkTourContentResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Singleton
class RetrofitTourNetwork @Inject constructor(
    networkJson: Json,
) : TourNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(AREA_BASED_LIST_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RetrofitTourNetworkApi::class.java)

    override suspend fun fetchTourInfoByRegion(
        tourInfoByRegionRequestBody: TourInfoByRegionRequestBody,
        arrangeOption: ArrangeOption,
    ): Result<NetworkTourContentResponse> = runCatching {
        val response = networkApi.fetchTourInfoByRegion(
            queryParams = tourInfoByRegionRequestBody.toQueryMap().putCommonQueryParams(
                arrange = arrangeOption.code,
            ),
        )
        if (response.isSuccessful) {
            response.body() ?: throw NullPointerException()
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun fetchFestivalInfo(
        festivalInfoRequestBody: FestivalInfoRequestBody,
        arrangeOption: ArrangeOption,
    ): Result<NetworkFestivalResponse> = runCatching {
        val response = networkApi.fetchFestivalInfo(
            queryParams = festivalInfoRequestBody.toQueryMap().putCommonQueryParams(
                arrange = arrangeOption.code,
            ),
        )
        if (response.isSuccessful) {
            response.body() ?: throw NullPointerException()
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun searchKeyword(
        keywordSearchRequestBody: KeywordSearchRequestBody,
        arrangeOption: ArrangeOption,
    ): Result<NetworkKeywordSearchResponse> = runCatching {
        val response = networkApi.searchKeyword(
            queryParams = keywordSearchRequestBody.toQueryMap().putCommonQueryParams(
                arrange = arrangeOption.code,
            ),
        )
        if (response.isSuccessful) {
            response.body() ?: throw NullPointerException()
        } else {
            throw Exception(response.message())
        }
    }

    private fun Map<String, String>.putCommonQueryParams(
        mobileOs: String = TOUR_API_MOBILE_OS,
        mobileApp: String = TOUR_API_MOBILE_APP,
        serviceKey: String = TOUR_API_SERVICE_KEY,
        responseType: String = RESPONSE_TYPE,
        arrange: String = ArrangeOption.MODIFIED_TIME.code,
    ): Map<String, String> {
        val commonQueryParams = mapOf(
            "MobileOS" to mobileOs,
            "MobileApp" to mobileApp,
            "serviceKey" to serviceKey,
            "_type" to responseType,
            "arrange" to arrange,
        )
        return this.toMutableMap().apply {
            putAll(commonQueryParams)
        }
    }

    private companion object {

        private const val AREA_BASED_LIST_URL = BuildConfig.TOUR_API_BASED_URL
        private const val TOUR_API_SERVICE_KEY = BuildConfig.TOUR_API_SERVICE_KEY
        private const val TOUR_API_MOBILE_APP = "wheretogo"
        private const val TOUR_API_MOBILE_OS = "AND"
        private const val RESPONSE_TYPE = "json"
    }
}

private interface RetrofitTourNetworkApi {

    @GET("areaBasedList1")
    suspend fun fetchTourInfoByRegion(
        @QueryMap queryParams: Map<String, String>,
    ): Response<NetworkTourContentResponse>

    @GET("searchFestival1")
    suspend fun fetchFestivalInfo(
        @QueryMap queryParams: Map<String, String>,
    ): Response<NetworkFestivalResponse>

    @GET("searchKeyword1")
    suspend fun searchKeyword(
        @QueryMap queryParams: Map<String, String>,
    ): Response<NetworkKeywordSearchResponse>
}
