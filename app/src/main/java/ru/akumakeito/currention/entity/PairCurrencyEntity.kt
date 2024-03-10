package ru.akumakeito.currention.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.akumakeito.currention.domain.FiatCurrency
import ru.akumakeito.currention.domain.PairCurrency
import ru.akumakeito.currention.dto.ConvertFiatResponse

@Entity
data class PairCurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @Embedded(prefix = "from_")
    val fromCurrency : FiatCurrency,
    @Embedded(prefix = "to_")
    val toCurrency : FiatCurrency,
    val toCurrencyLastRate : Double,
    val toCurrencyNewRate : Double,
    val rateCurrency : Float
) {
    fun toDto() = PairCurrency(
        id,
        fromCurrency,
        toCurrency,
        toCurrencyLastRate,
        toCurrencyNewRate,
        rateCurrency
    )

    companion object {
        fun fromDto(pairCurrency: PairCurrency) = PairCurrencyEntity(
            pairCurrency.id,
            pairCurrency.fromCurrency,
            pairCurrency.toCurrency,
            pairCurrency.toCurrencyLastRate,
            pairCurrency.toCurrencyNewRate,
            pairCurrency.rateCurrency
        )

//        fun fromResponse(convertFiatResponse: ConvertFiatResponse) = PairCurrencyEntity(
//            0,
//            convertFiatResponse.from,
//            convertFiatResponse.to,
//            0.0,
//            convertFiatResponse.value,
//            0.0
//        )
    }
}
fun List<PairCurrencyEntity>.toDto() : List<PairCurrency> = map(PairCurrencyEntity::toDto)

