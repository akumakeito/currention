package ru.akumakeito.currention.util

import okhttp3.Interceptor
import okhttp3.Response
import ru.akumakeito.currention.api.API_KEY
import ru.akumakeito.currention.api.AUTH_HEADER
import javax.inject.Inject

class QueryParameterInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter(AUTH_HEADER, API_KEY)
            .build()

        request = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)

    }
}