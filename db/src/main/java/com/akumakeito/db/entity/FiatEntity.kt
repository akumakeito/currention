package com.akumakeito.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonres.R

@Entity
data class FiatEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name_ru")
    val nameRu: String,
    @ColumnInfo(name = "name_en")
    val nameEn: String,
    @ColumnInfo(name = "short_code")
    val shortCode: String,
    val code: String,
    val symbol: String,
    var flag: Int = R.drawable.flag_currention,
    var isPopular: Boolean = false,
    var isFavorite: Boolean = false
) {
    fun toModel() = FiatCurrency(
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
}

fun List<FiatEntity>.toModel(): List<FiatCurrency> = map(FiatEntity::toModel)
