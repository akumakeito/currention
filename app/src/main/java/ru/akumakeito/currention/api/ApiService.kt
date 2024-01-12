package ru.akumakeito.currention.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.akumakeito.currention.dto.FiatResponse
import ru.akumakeito.currention.dto.FiatServerResponse


interface ApiService {
    @GET("currencies")
    suspend fun getCurrencyList(@Query("type") type : String) : FiatServerResponse

    @GET("latest")
    suspend fun getLatest() : List<FiatResponse>

}