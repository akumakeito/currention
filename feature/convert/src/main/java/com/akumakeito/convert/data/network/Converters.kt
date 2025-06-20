package com.akumakeito.convert.data.network

import com.akumakeito.core.models.domain.FiatCurrency
import com.akumakeito.convert.data.network.dto.FiatResponse
import com.akumakeito.db.entity.FiatEntity


fun FiatResponse.fromResponse() = FiatEntity(
    nameRu = "",
    nameEn = name,
    shortCode = shortCode,
    code = code,
    symbol = symbol,
)


fun List<FiatResponse>.toEntity(): List<FiatEntity> = map { it.fromResponse() }

fun FiatCurrency.toEntity() = FiatEntity(
    nameRu = nameRu,
    nameEn = nameEn,
    shortCode = shortCode,
    code = code,
    symbol = symbol,
    flag = flag,
    isPopular = isPopular,
    isFavorite = isFavorite,
    rates = this.rates
)