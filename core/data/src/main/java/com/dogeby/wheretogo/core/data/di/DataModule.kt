package com.dogeby.wheretogo.core.data.di

import com.dogeby.wheretogo.core.data.repository.SearchKeywordRepository
import com.dogeby.wheretogo.core.data.repository.SearchKeywordRepositoryImpl
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.data.repository.TourRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesTourRepository(tourRepository: TourRepositoryImpl): TourRepository = tourRepository

    @Provides
    @Singleton
    fun providesSearchKeywordRepository(
        searchKeywordRepository: SearchKeywordRepositoryImpl,
    ): SearchKeywordRepository = searchKeywordRepository
}
