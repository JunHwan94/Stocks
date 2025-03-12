package com.zzunapps.stocks.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzunapps.stocks.BuildConfig
import com.zzunapps.stocks.data.OverseasPriceResponse
import com.zzunapps.stocks.data.StockItem
import com.zzunapps.stocks.network.KISService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockViewModel : ViewModel() {
    val TAG = "StockViewModel"
    val allStocks = MutableLiveData<List<StockItem>>()

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://openapi.koreainvestment.com:9443/").addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(KISService::class.java)

        allStocks.value = mutableListOf<StockItem>()
        service.getStockData(
            mapOf(
                "content-type" to "application/json; charset=utf-8",
                "authorization" to "Bearer ACCESSTOKEN",
                "appkey" to BuildConfig.APP_KEY,
                "appsecret" to BuildConfig.APP_SECRET,
                "tr_id" to "HHDFS76200200"
            ),
            mapOf(
                "AUTH" to "", // 빈 값으로 보내야 함
                "EXCD" to "NAS", // 거래소 코드 NAS, NYS...
                "SYMB" to "AAPL", // 종목 코드 AAPL, TRMD
            )
        ).enqueue(object : retrofit2.Callback<OverseasPriceResponse> {
            override fun onResponse(call: Call<OverseasPriceResponse>, response: Response<OverseasPriceResponse>) {
                if(response.isSuccessful) {
                    val priceResponse = response.body()!!
                    Log.d(TAG, "${response.body()}")
                    val stockItem = StockItem(
                        priceResponse.priceDetail.rsym.takeLast(4),
                        priceResponse.priceDetail.last.toDouble()
                    )
                    Log.d(TAG, "${stockItem.stockName} ${stockItem.price}")
                    (allStocks.value as MutableList<StockItem>).add(stockItem)
                }
            }

            override fun onFailure(call: Call<OverseasPriceResponse>, t: Throwable) {
                Log.d("error", "${t.message}")
            }
        })
    }
}
