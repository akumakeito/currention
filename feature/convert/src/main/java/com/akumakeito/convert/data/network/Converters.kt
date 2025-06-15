package com.akumakeito.convert.data.network

import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.convert.data.network.dto.FiatResponse
import com.akumakeito.db.entity.FiatEntity


fun FiatResponse.fromResponse() = FiatEntity(
    id = id,
    nameRu = "",
    nameEn = name,
    shortCode = shortCode,
    code = code,
    symbol = symbol,
)


fun List<FiatResponse>.toEntity(): List<FiatEntity> = map { it.fromResponse() }

fun FiatCurrency.toEntity() = FiatEntity(
    id = id,
    nameRu = nameRu,
    nameEn = nameEn,
    shortCode = shortCode,
    code = code,
    symbol = symbol,
    flag = flag,
    isPopular = isPopular,
    isFavorite = isFavorite
)