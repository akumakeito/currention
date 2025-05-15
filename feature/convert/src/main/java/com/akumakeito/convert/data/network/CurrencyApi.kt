package com.akumakeito.convert.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import com.akumakeito.convert.data.network.dto.ConvertFiatOnDateServerResponse
import com.akumakeito.convert.data.network.dto.ConvertFiatServerResponse
import com.akumakeito.convert.data.network.dto.FiatResponse
import com.akumakeito.convert.data.network.dto.FiatServerResponse


interface CurrencyApi {
    @GET("currencies")
    suspend fun getCurrencyList(@Query("type") type: String): FiatServerResponse

    @GET("latest")
    suspend fun getLatest(): List<FiatResponse>

    @GET("convert")
    suspend fun getPairRates(
        @Query("from") currencyFromShortCode: String,
        @Query("to") currencyToShortCode: String,
        @Query("amount") amount: Double
    ): ConvertFiatServerResponse

    @GET("historical")
    suspend fun getCurrencyRateOnDate(
        @Query("base") currencyFromShortCode: String,
        @Query("date") date: String,
        @Query("symbols") currencyToShortCode: List<String>
    ): ConvertFiatOnDateServerResponse

}