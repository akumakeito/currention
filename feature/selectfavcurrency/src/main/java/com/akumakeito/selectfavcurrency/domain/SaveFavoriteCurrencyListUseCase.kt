package com.akumakeito.selectfavcurrency.domain

import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.core.appsettings.AppSettingsRepository
import com.akumakeito.core.di.ApplicationScope
import com.akumakeito.core.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveFavoriteCurrencyListUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val currencyRepository: CurrencyRepository,
    private val convertRepository: ConvertRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) {
    init {
        log("SaveFavoriteCurrencyListUseCase init")
    }
    fun runAsync() = applicationScope.launch(Dispatchers.IO) {
        invoke()
    }
    private suspend operator fun invoke() {
        appSettingsRepository.setFirstStart(false)
        val lastSelectedBaseCurrencyShortCode = appSettingsRepository.getLastSelectedBaseCurrency()
        log("lastSelectedBaseCurrencyShortCode: $lastSelectedBaseCurrencyShortCode")
        val favoriteCurrencies = currencyRepository.getFavoriteCurrencyList()
        log("favoriteCurrencies: $favoriteCurrencies")

        val currentFavoriteShortCodes = favoriteCurrencies[0].rates.keys
        val updatedFavoriteCurrencies = favoriteCurrencies.map {
            it.shortCode
        }.toSet()
        
        if (currentFavoriteShortCodes == updatedFavoriteCurrencies) {
            return
        }

        favoriteCurrencies.forEach {
            log("favCur: $it")
            convertRepository.updateRates(it)
        }
        val shouldUpdateBaseCurrency =
            favoriteCurrencies.find { it.shortCode == lastSelectedBaseCurrencyShortCode } == null

        if (shouldUpdateBaseCurrency) {
            appSettingsRepository.setLastSelectedBaseCurrency(favoriteCurrencies.first().shortCode)
        }
    }
}