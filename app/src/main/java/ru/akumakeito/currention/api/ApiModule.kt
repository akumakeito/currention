package ru.akumakeito.currention.api

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.akumakeito.currention.BuildConfig
import ru.akumakeito.currention.util.QueryParameterInterceptor
import javax.inject.Singleton


const val BASE_URL = "https://api.currencybeacon.com/v1/"
const val API_KEY = BuildConfig.authApiKey
const val AUTH_HEADER = "api_key"


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun providesGson() : Gson = Gson()

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
    ): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

}