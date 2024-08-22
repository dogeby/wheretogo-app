package com.dogeby.wheretogo.core.database.di

import android.content.Context
import androidx.room.Room
import com.dogeby.wheretogo.core.database.WheretogoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesRelicCalculatorDatabase(@ApplicationContext context: Context): WheretogoDatabase =
        Room.databaseBuilder(
            context,
            WheretogoDatabase::class.java,
            "wheretogo-database",
        ).build()
}
