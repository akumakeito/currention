package com.akumakeito.convert.di

import com.akumakeito.convert.data.network.CurrencyApi
import com.akumakeito.convert.data.network.QueryParameterInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.saket.swipe.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://api.currencybeacon.com/v1/"
//val API_KEY = BuildConfig.AUTH_API_KEY
val API_KEY = "Oh3OnAu238hxbBKhap3SSogpSt7La4xj"
const val AUTH_HEADER = "api_key"


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()

    @Provides
    @Singleton
    fun providesLogging(): Interceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    @Singleton
    fun providesOkhttp(
        loggingInterceptor: Interceptor,
        queryParameterInterceptor: QueryParameterInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(queryParameterInterceptor)
        .build()


    @Provides
    @Singleton
    fun providesApiService(
        okHttpClient: OkHttpClient,
    ): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(CurrencyApi::class.java)

}