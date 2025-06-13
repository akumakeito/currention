package com.akumakeito.convert.data.network

import com.akumakeito.convert.data.network.dto.ConvertFiatOnDateServerResponse
import com.akumakeito.convert.data.network.dto.ConvertFiatServerResponse
import com.akumakeito.convert.data.network.dto.FiatServerResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApi {
    @GET("currencies")
    suspend fun getCurrencyList(@Query("type") type: String): FiatServerResponse

    @GET("latest")
    suspend fun getLatest(
        @Query("base") currencyFromShortCode: String,
        @Query("symbols") currencyToShortCodeList: List<String>
    ): ConvertFiatOnDateServerResponse

    @GET("latest")
    suspend fun getLatest(
        @Query("base") currencyFromShortCode: String,
    ): ConvertFiatOnDateServerResponse

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