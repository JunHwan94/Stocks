package com.zzunapps.stocks.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://openapi.koreainvestment.com:9443/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val service by lazy { retrofit.create(KISService::class.java) }
}