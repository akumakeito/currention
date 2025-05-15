package com.akumakeito.convert.data.network

import com.akumakeito.convert.di.API_KEY
import com.akumakeito.convert.di.AUTH_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class QueryParameterInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(AUTH_HEADER, API_KEY)
            .build()

        request = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}