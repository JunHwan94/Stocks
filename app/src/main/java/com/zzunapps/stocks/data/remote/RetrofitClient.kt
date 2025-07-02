package com.zzunapps.stocks.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zzunapps.stocks.data.remote.api.AuthService
import com.zzunapps.stocks.features.widget.data.remote.api.StockService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType // MediaType.get() 대신 toMediaType() 권장
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    // 로깅 인터셉터 추가
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // 요청과 응답의 본문까지 상세히 로깅
    }

    // OkHttpClient에 인터셉터 추가
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://openapi.koreainvestment.com:9443/")
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    val authService by lazy { retrofit.create(AuthService::class.java) }
    val stockService by lazy {retrofit.create(StockService::class.java) }
}