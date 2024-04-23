package ru.akumakeito.currention.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.akumakeito.currention.dto.ConvertFiatOnDateServerResponse
import ru.akumakeito.currention.dto.ConvertFiatServerResponse
import ru.akumakeito.currention.dto.FiatResponse
import ru.akumakeito.currention.dto.FiatServerResponse


interface ApiService {
    @GET("currencies")
    suspend fun getCurrencyList(@Query("type") type : String) : FiatServerResponse

    @GET("latest")
    suspend fun getLatest() : List<FiatResponse>

    @GET("convert")
    suspend fun getPairRates(@Query("from")currencyFromShortCode : String, @Query("to")currencyToShortCode : String, @Query("amount") amount : Double) : ConvertFiatServerResponse

    @GET("historical")
    suspend fun getCurrencyRateOnDate(@Query("base")currencyFromShortCode : String, @Query("date")date : String, @Query("symbols") currencyToShortCode : List<String>) : ConvertFiatOnDateServerResponse

}