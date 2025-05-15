package com.akumakeito.db.di

import android.content.Context
import androidx.room.Room
import com.akumakeito.db.AppDb
import com.akumakeito.db.dao.CurrencyDao
import com.akumakeito.db.dao.CurrencyPairDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): AppDb = Room.databaseBuilder(
        context,
        AppDb::class.java,
        "app.db"
    )
        .fallbackToDestructiveMigration(false)
        .build()

    @Provides
    @Singleton
    fun provideCurrencyDao(
        appDb: AppDb
    ): CurrencyDao = appDb.provideCurrencyDao()

    @Provides
    @Singleton
    fun provideCurrencyPairDao(
        appDb: AppDb
    ): CurrencyPairDao = appDb.provideCurrencyPairDao()
}