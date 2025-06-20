package com.akumakeito.convert.domain

import com.akumakeito.core.models.domain.FiatCurrency
import java.io.IOException
import javax.inject.Inject

class ConvertBaseCurrencyToFavCurrencyUseCase @Inject constructor(
    private val convertRepository: ConvertRepository
) {

    suspend operator fun invoke(
        baseCurrency: FiatCurrency,
        convertingList: List<FiatCurrency>,
        amountString: String
    ): Result<List<FiatCurrency>> {
        return try {
            val amount = amountString.toDoubleOrNull() ?: return Result.failure(
                IllegalArgumentException("Invalid amount")
            )
            val rates = convertRepository.getLatest(baseCurrency).getOrThrow()

            val result = convertingList.map { currency ->
                val rate = rates[currency.shortCode] ?: return Result.failure(
                    IllegalArgumentException("Rate not found for ${currency.shortCode}")
                )
                currency.copy().also {
                    it.currentRate = rate * amount
                }
            }
            Result.success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(e)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}