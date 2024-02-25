package ru.akumakeito.currention.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.akumakeito.currention.R
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.FiatResponse

@Entity
data class FiatEntity(
    @PrimaryKey
    val id : Int,
    val name : String,
    @ColumnInfo(name = "short_code")
    val shortCode: String,
    val code : String,
    val symbol : String,
    var flag : Int = R.drawable.flag_currention,
    var isPopular : Boolean = false,
    var isFavorite : Boolean = false
) {
    fun toDto() = FiatCurrency(
        id,
        name,
        shortCode,
        code,
        symbol,
        flag,
        isPopular,
        isFavorite
    )

    companion object {

    fun fromResponse(fiatResponse: FiatResponse) = FiatEntity(
        fiatResponse.id,
        fiatResponse.name,
        fiatResponse.shortCode,
        fiatResponse.code,
        fiatResponse.symbol,
    )
        fun fromDto(fiatCurrency: FiatCurrency) = FiatEntity(
            fiatCurrency.id,
            fiatCurrency.name,
            fiatCurrency.shortCode,
            fiatCurrency.code,
            fiatCurrency.symbol
        )
    }
}

fun List<FiatEntity>.toDto() : List<FiatCurrency> = map(FiatEntity::toDto)
//fun List<FiatCurrency>.toEntity() : List<FiatEntity> = map(FiatEntity::fromDto)
fun List<FiatResponse>.toEntity() : List<FiatEntity> = map(FiatEntity::fromResponse)