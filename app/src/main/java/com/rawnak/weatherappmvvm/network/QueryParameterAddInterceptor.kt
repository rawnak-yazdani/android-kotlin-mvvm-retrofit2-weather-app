package com.rawnak.weatherappmvvm.network

import com.rawnak.weatherappmvvm.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

// adding query parameter with Interceptor
class QueryParameterAddInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url().newBuilder()
            .addQueryParameter("appid", BuildConfig.APP_ID)
            .build()

        val request = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer token")
            .url(url)
            .build()

        val respose = chain.proceed(request)

        return respose
    }
}