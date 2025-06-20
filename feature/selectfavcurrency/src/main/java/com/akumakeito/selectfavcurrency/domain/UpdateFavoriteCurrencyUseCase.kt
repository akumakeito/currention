package com.akumakeito.selectfavcurrency.domain

import com.akumakeito.convert.domain.ConvertRepository
import com.akumakeito.convert.domain.CurrencyRepository
import javax.inject.Inject

class UpdateFavoriteCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val convertRepository: ConvertRepository,
) {
    suspend operator fun invoke() {
        val favoriteCurrencies = currencyRepository.getFavoriteCurrencyList()
        favoriteCurrencies.forEach {
            convertRepository.updateRates(it)
        }
    }
}