package ru.akumakeito.currention.repository

import ru.akumakeito.currention.api.ApiService
import ru.akumakeito.currention.dao.CurrencyDao
import ru.akumakeito.currention.dto.CurrencyType
import ru.akumakeito.currention.dto.FiatCurrency
import ru.akumakeito.currention.entity.toDto
import ru.akumakeito.currention.entity.toEntity
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao : CurrencyDao
) : CurrencyRepository {
    override suspend fun getLatest() {

    }

    override suspend fun getFiatCurrencyList(): List<FiatCurrency> {
        if (dao.isEmpty()) {
            try {
                val result = apiService.getCurrencyList(CurrencyType.FIAT.name.lowercase())
                dao.insertAllFiat(result.response.toEntity())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return dao.getAllFiat().toDto()
    }
}