package com.zzunapps.stocks.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.zzunapps.stocks.BuildConfig
import com.zzunapps.stocks.data.AccessTokenRequestBody
import com.zzunapps.stocks.data.AccessTokenResponseBody
import com.zzunapps.stocks.data.PriceDetail
import com.zzunapps.stocks.data.StockItem
import com.zzunapps.stocks.network.KISService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class WidgetWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params){
    val allStocks = MutableLiveData<List<StockItem>>()

    private val retrofit = Retrofit.Builder().baseUrl("https://openapi.koreainvestment.com:9443/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val service = retrofit.create(KISService::class.java)

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result  = withContext(Dispatchers.IO) {
        try {
            // todo : 토큰은 SharedPreferences 에 저장
            //  토큰 기간 만료되었으면 재발급
            val accessToken = getAccessToken().accessToken

            // todo : DB에 저장된 목록 불러와서 종목별 요청 후,
            //  리스트에 담아서 반환?
            //  이렇게 하지 말고 리스트는 위젯클래스에서 가지고, 주식 api 요청 하나당 한번의 worker 수행?, retrofit 객체는 클래스로 따로 빼서 관리
            getPriceDetail(accessToken, "NAS","TRMD")

            val resultData = Data.Builder()
                .put("result", allStocks)
                .build()

            Result.success(resultData)
        } catch(e: Exception) {
            Result.failure()
        }
    }

    private suspend fun getAccessToken() : AccessTokenResponseBody {
        return withContext(Dispatchers.IO) {
            service.getAccessToken(
                AccessTokenRequestBody()
            ).await()
        }
    }

    private suspend fun getPriceDetail(
        accessToken: String,
        excd: String = "NAS",
        symb: String = "AAPL"
    ) : PriceDetail {
        return withContext(Dispatchers.IO) {
            service.getStockData(
                mapOf(
                    CONTENT_TYPE_KV,
                    AUTHORIZATION to accessToken,
                    APP_KEY_KV,
                    APP_SECRET_KV,
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

    private companion object {
        private const val AUTHORIZATION = "authorization"
        private val CONTENT_TYPE_KV = "content-type" to "application/json; charset=utf-8"
        private val APP_KEY_KV = "appkey" to BuildConfig.APP_KEY
        private val APP_SECRET_KV = "appsecret" to BuildConfig.APP_SECRET
    }
}