package com.dogeby.wheretogo.core.network.retrofit

import com.dogeby.wheretogo.core.model.tour.ArrangeOption
import com.dogeby.wheretogo.core.network.BuildConfig
import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.model.tour.NetworkTourResponse
import com.dogeby.wheretogo.core.network.model.tour.TourInfoRequestBody
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
        currentPage: Int,
        numberOfRows: Int,
        contentTypeId: String,
        areaCode: String,
        sigunguCode: String,
        category1: String,
        category2: String,
        category3: String,
        arrangeOption: ArrangeOption,
    ): Result<NetworkTourResponse> = runCatching {
        val response = networkApi.fetchTourInfoByRegion(
            TourInfoRequestBody(
                numberOfRows = numberOfRows,
                currentPage = currentPage,
                mobileOs = TOUR_API_MOBILE_OS,
                mobileApp = TOUR_API_MOBILE_APP,
                serviceKey = TOUR_API_SERVICE_KEY,
                responseType = RESPONSE_TYPE,
                arrange = arrangeOption.code,
                contentTypeId = contentTypeId,
                areaCode = areaCode,
                sigunguCode = sigunguCode,
                category1 = category1,
                category2 = category2,
                category3 = category3,
            ).toQueryMap(),
        )
        if (response.isSuccessful) {
            response.body() ?: throw NullPointerException()
        } else {
            throw Exception(response.message())
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
    ): Response<NetworkTourResponse>
}
