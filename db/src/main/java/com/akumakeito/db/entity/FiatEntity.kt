package com.akumakeito.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akumakeito.commonres.R
import com.akumakeito.core.models.domain.FiatCurrency

@Entity
data class FiatEntity(
    @PrimaryKey
    @ColumnInfo(name = "short_code")
    val shortCode: String,
    @ColumnInfo(name = "name_ru")
    val nameRu: String,
    @ColumnInfo(name = "name_en")
    val nameEn: String,
    val code: String,
    val symbol: String,
    var flag: Int = R.drawable.flag_currention,
    var isPopular: Boolean = false,
    var isFavorite: Boolean = false,
    var rates: Map<String, Double>? = null
) {
    fun toModel() = FiatCurrency(
        nameRu = nameRu,
        nameEn = nameEn,
        shortCode = shortCode,
        code = code,
        symbol = symbol,
        flag = flag,
        isPopular = isPopular,
        isFavorite = isFavorite
    ).apply {
        rates = this@FiatEntity.rates ?: emptyMap()
    }
}

fun List<FiatEntity>.toModel(): List<FiatCurrency> = map(FiatEntity::toModel)
