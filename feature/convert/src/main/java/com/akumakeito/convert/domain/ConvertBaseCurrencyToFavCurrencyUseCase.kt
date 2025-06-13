package com.akumakeito.convert.domain

import com.akumakeito.commonmodels.domain.FiatCurrency
import javax.inject.Inject

class ConvertBaseCurrencyToFavCurrencyUseCase @Inject constructor(
    private val convertRepository: ConvertRepository ) {

    suspend operator fun invoke(baseCurrency: FiatCurrency, convertToList: List<FiatCurrency>, amountString : String) : List<FiatCurrency> {
        val amount = amountString.toDouble()
        val result = convertRepository.getLatest(baseCurrency, convertToList).rates
        return convertToList.map {
            it.apply {
                rate = result[it.shortCode]?.times(amount) ?: 0.0
            }
        }
    }
}