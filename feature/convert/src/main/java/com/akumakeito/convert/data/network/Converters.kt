package com.akumakeito.convert.data.network

import com.akumakeito.convert.data.network.dto.FiatResponse
import com.akumakeito.db.entity.FiatEntity


fun FiatResponse.fromResponse() = FiatEntity(
    id,
    name,
    shortCode,
    code,
    symbol,
)


fun List<FiatResponse>.toEntity(): List<FiatEntity> = map { it.fromResponse() }