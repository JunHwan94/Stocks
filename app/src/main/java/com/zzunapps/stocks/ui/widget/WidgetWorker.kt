package com.zzunapps.stocks.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.zzunapps.stocks.BuildConfig
import com.zzunapps.stocks.data.Constants.AUTHORIZATION
import com.zzunapps.stocks.data.Constants.TR_ID
import com.zzunapps.stocks.data.OverseasPriceResponse
import com.zzunapps.stocks.network.RetrofitClient
import com.zzunapps.stocks.ui.checkAndUpdateAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WidgetWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params){

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result  = withContext(Dispatchers.IO) {
        checkAndUpdateAccessToken(applicationContext)
        try {
            val accessToken = applicationContext.getSharedPreferences("token", Context.MODE_PRIVATE).getString("accessToken", "") ?: ""
            Log.d("WidgetWorker", "accessToken: $accessToken")

            var symbol = ""
            var price = 0.0
            // todo : SharedPreferences 에 저장된 목록 불러와서 종목별 요청
            requestOverseasPrice(accessToken, "NAS","TRMD") {
                symbol = it.priceDetail.rsym
                price = it.priceDetail.last.toDouble()
            }

            Log.d("WidgetWorker", "symbol: $symbol, price: $price")

            val resultData = Data.Builder()
                .putAll(
                    mutableMapOf<String, Any>(
                        "symbol" to symbol,
                        "price" to price
                    )
                )
                .build()

            Result.success(resultData)
        } catch(e: Exception) {
            Log.d("WidgetWorker", "Error fetching data: ${e.stackTrace}")
            Result.failure()
        }
    }

    private suspend fun requestOverseasPrice(
        accessToken: String,
        excd: String = "NAS",
        symb: String = "AAPL",
        processResponseBody: (OverseasPriceResponse) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val response = RetrofitClient.service.getStockData(
                mapOf(
                    CONTENT_TYPE_KV,
                    AUTHORIZATION to "Bearer $accessToken",
                    APP_KEY_KV,
                    APP_SECRET_KV,
                    TR_ID to "HHDFS76200200"
                ),
                mapOf(
                    "AUTH" to "", // 빈 값으로 보내야 함
                    "EXCD" to excd, // 거래소 코드 NAS, NYS...
                    "SYMB" to symb, // 종목 코드 AAPL, TRMD
                )
            )
            if(response.isSuccessful) {
                val body = response.body() ?: throw Exception("Empty response body")
                processResponseBody(body)
            } else {
                response.errorBody()
                throw Exception("Error getting overseas price : ${response.code()}")
            }
        }
    }

    private companion object {
        private val CONTENT_TYPE_KV = "content-type" to "application/json; charset=utf-8"
        private val APP_KEY_KV = "appkey" to BuildConfig.APP_KEY
        private val APP_SECRET_KV = "appsecret" to BuildConfig.APP_SECRET
    }
}