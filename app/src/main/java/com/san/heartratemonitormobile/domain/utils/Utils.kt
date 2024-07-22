package com.san.heartratemonitormobile.domain.utils

import com.san.heartratemonitormobile.data.remote.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Utils {
    fun getRetrofit(token: String) = Retrofit.Builder()
        .baseUrl("http://13.125.53.53:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClientWithOrWithoutIdToken(token))
        .build()

    fun getRetrofit() = Retrofit.Builder()
        .baseUrl("http://13.125.53.53:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClientWithOrWithoutIdToken(null))
        .build()

    private fun getClientWithOrWithoutIdToken(token: String?): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)

        if (token == null) return builder.build()

        builder.addInterceptor(HeaderInterceptor(token))
        return builder.build()
    }
}