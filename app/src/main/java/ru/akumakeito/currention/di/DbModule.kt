package ru.akumakeito.currention.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.akumakeito.currention.data.db.AppDb
import ru.akumakeito.currention.data.db.dao.CurrencyDao
import ru.akumakeito.currention.data.db.dao.CurrencyPairDao
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
        .fallbackToDestructiveMigration()
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
