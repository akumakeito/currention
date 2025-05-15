package com.akumakeito.rates.di

import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.db.dao.CurrencyPairDao
import com.akumakeito.rates.data.PairCurrencyRepositoryImpl
import com.akumakeito.rates.domain.PairCurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PairCurrencyModule {

    @Provides
    @Singleton
    fun providePairCurrencyRepository(
        pairCurrencyDao: CurrencyPairDao,
        convertRepository: ConvertRepository
    ): PairCurrencyRepository {
        return PairCurrencyRepositoryImpl(pairCurrencyDao, convertRepository)
    }
}