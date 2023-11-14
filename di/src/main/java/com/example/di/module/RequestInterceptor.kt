package com.example.di.module

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url
            .newBuilder()
            .build()
        val request = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}