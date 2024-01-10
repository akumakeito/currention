package ru.akumakeito.currention.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCurrencyRepositoryImpl(currencyRepositoryImpl: CurrencyRepositoryImpl) : CurrencyRepository
}