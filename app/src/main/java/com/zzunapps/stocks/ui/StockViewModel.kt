package com.zzunapps.stocks.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzunapps.stocks.BuildConfig
import com.zzunapps.stocks.data.AccessTokenRequestBody
import com.zzunapps.stocks.data.AccessTokenResponseBody
import com.zzunapps.stocks.data.PriceDetail
import com.zzunapps.stocks.data.StockItem
import com.zzunapps.stocks.network.KISService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class StockViewModel : ViewModel() {
    val TAG = "StockViewModel"
    val allStocks = MutableLiveData<List<StockItem>>()

    private val retrofit = Retrofit.Builder().baseUrl("https://openapi.koreainvestment.com:9443/").addConverterFactory(GsonConverterFactory.create()).build()
    private val service = retrofit.create(KISService::class.java)

    init {
        allStocks.value = mutableListOf<StockItem>()

        GlobalScope.launch {
            val priceDetail = getPriceDetail(
                accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjQ0Y2RjMGM2LWIxMmMtNDE3MS1iODE1LWM2NzgzMmE2YmYyMyIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTc0MjM5MjI4OCwiaWF0IjoxNzQyMzA1ODg4LCJqdGkiOiJQU1dQN0EwaUR1dFoxWlR4eUtLS1RucGsyaUxMTVlVam9nVXAifQ.KaL8Qu2oZB2DBbecExQ-1d8j9fGZtO70fl00PPniMvm8rJycYXcrsMooL_0NzZkz2XHPVuLyKj1a1RZeRkGcmA",
                excd = "NAS",
                symb = "TRMD"
            )
            (allStocks.value as MutableList<StockItem>).add(StockItem(priceDetail.rsym.takeLast(4), priceDetail.last.toDouble()))
        }
    }

    suspend fun getAccessToken() : AccessTokenResponseBody {
        return withContext(Dispatchers.IO) {
            service.getAccessToken(
                AccessTokenRequestBody()
            ).await()
        }
    }

    suspend fun getPriceDetail(
        accessToken: String,
        excd: String = "NAS",
        symb: String = "AAPL"
    ) : PriceDetail {
        return withContext(Dispatchers.IO) {
            service.getStockData(
                mapOf(
                    "content-type" to "application/json; charset=utf-8",
                    "authorization" to accessToken,
                    "appkey" to BuildConfig.APP_KEY,
                    "appsecret" to BuildConfig.APP_SECRET,
                    "tr_id" to "HHDFS76200200"
                ),
                mapOf(
                    "AUTH" to "", // 빈 값으로 보내야 함
                    "EXCD" to excd, // 거래소 코드 NAS, NYS...
                    "SYMB" to symb, // 종목 코드 AAPL, TRMD
                )
            ).await().priceDetail
        }
    }

    companion object {
        private const val AUTHORIZATION = "authorization"
        private val CONTENT_TYPE_KV = "content-type" to "application/json; charset=utf-8"
        private val APP_KEY_KV = "appkey" to BuildConfig.APP_KEY
        private val APP_SECRET_KV = "appsecret" to BuildConfig.APP_SECRET
    }
}
