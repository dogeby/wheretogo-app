package com.dogeby.wheretogo.core.database.di

import com.dogeby.wheretogo.core.database.WheretogoDatabase
import com.dogeby.wheretogo.core.database.dao.SearchKeywordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun providesSearchKeywordDao(database: WheretogoDatabase): SearchKeywordDao =
        database.searchKeywordDao()
}
