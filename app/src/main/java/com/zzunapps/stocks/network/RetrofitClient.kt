package com.zzunapps.stocks.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://openapi.koreainvestment.com:9443/")
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }
    val service by lazy { retrofit.create(KISService::class.java) }
}