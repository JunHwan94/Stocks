package com.zzunapps.stocks.network

import com.zzunapps.stocks.data.AccessTokenRequestBody
import com.zzunapps.stocks.data.AccessTokenResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun requestAccessToken(processResponseBody: (AccessTokenResponseBody) -> Unit) {
        withContext(Dispatchers.IO) {
            val response = service.getAccessToken(AccessTokenRequestBody())

            if(response.isSuccessful) {
                val body = response.body()
                processResponseBody(body!!)
            } else {
                response.errorBody()
                throw Exception("Error getting access token : ${response.code()}")
            }
        }
    }
}