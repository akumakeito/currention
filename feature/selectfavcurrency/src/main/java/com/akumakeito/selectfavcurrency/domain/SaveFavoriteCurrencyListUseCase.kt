package com.akumakeito.selectfavcurrency.domain

import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.core.appsettings.AppSettingsRepository
import javax.inject.Inject

class SaveFavoriteCurrencyListUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val currencyRepository: CurrencyRepository,
    private val convertRepository: ConvertRepository,
) {
    suspend operator fun invoke() {
        appSettingsRepository.setFirstStart(false)
        val lastSelectedBaseCurrencyShortCode = appSettingsRepository.getLastSelectedBaseCurrency()
        val favoriteCurrencies = currencyRepository.getFavoriteCurrencyList()

        val currentFavoriteShortCodes = favoriteCurrencies[0].rates.keys
        val updatedFavoriteCurrencies = favoriteCurrencies.map {
            it.shortCode
        }.toSet()
        
        if (currentFavoriteShortCodes == updatedFavoriteCurrencies) {
            return
        }

        favoriteCurrencies.forEach {
            convertRepository.updateRates(it)
        }
        val shouldUpdateBaseCurrency =
            favoriteCurrencies.find { it.shortCode == lastSelectedBaseCurrencyShortCode } == null

        if (shouldUpdateBaseCurrency) {
            appSettingsRepository.setLastSelectedBaseCurrency(favoriteCurrencies.first().shortCode)
        }
    }
}