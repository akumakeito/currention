package ru.akumakeito.currention.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.akumakeito.currention.data.repository.CurrencyRepositoryImpl
import ru.akumakeito.currention.data.repository.PairCurrencyRepositoryImpl
import ru.akumakeito.currention.domain.repository.CurrencyRepository
import ru.akumakeito.currention.domain.repository.PairCurrencyRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCurrencyRepositoryImpl(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository

    @Binds
    @Singleton
    fun bindPairCurrencyRepositoryImpl(pairCurrencyRepositoryImpl: PairCurrencyRepositoryImpl): PairCurrencyRepository
}