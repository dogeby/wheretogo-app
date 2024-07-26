package com.dogeby.wheretogo.core.network.di

import com.dogeby.wheretogo.core.network.TourNetworkDataSource
import com.dogeby.wheretogo.core.network.retrofit.RetrofitTourNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesTourNetworkDataSource(
        retrofitTourNetwork: RetrofitTourNetwork,
    ): TourNetworkDataSource = retrofitTourNetwork
}
