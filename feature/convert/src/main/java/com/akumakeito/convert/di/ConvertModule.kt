package com.akumakeito.convert.di

import android.content.Context
import com.akumakeito.convert.data.ConvertRepositoryImpl
import com.akumakeito.convert.data.CurrencyRepositoryImpl
import com.akumakeito.convert.data.FlagDeserializer
import com.akumakeito.convert.data.FlagInteractor
import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.db.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ConvertModule {

    @Provides
    @Singleton
    fun provideFlagInteractor(
        flagDeserializer: FlagDeserializer,
        currencyDao: CurrencyDao
    ): FlagInteractor = FlagInteractor(flagDeserializer, currencyDao)

    @Provides
    @Singleton
    fun provideConvertRepository(
        currencyApi: CurrencyApi,
    ): ConvertRepository = ConvertRepositoryImpl(currencyApi)

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        currencyApi: CurrencyApi,
        currencyDao: CurrencyDao,
        flagInteractor: FlagInteractor,
        @ApplicationContext context: Context,
    ): CurrencyRepository =
        CurrencyRepositoryImpl(currencyApi, currencyDao, flagInteractor, context)
}