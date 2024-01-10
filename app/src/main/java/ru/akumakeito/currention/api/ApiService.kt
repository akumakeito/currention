package ru.akumakeito.currention.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.akumakeito.currention.dto.FiatCurrency
import ru.akumakeito.currention.dto.FiatResponse
import ru.akumakeito.currention.dto.ServerResponse


interface ApiService {
    @GET("currencies")
    suspend fun getCurrencyList(@Query("type") type : String) : ServerResponse

    @GET("latest")
    suspend fun getLatest() : List<FiatResponse>

}