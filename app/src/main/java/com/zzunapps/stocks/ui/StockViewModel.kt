package com.zzunapps.stocks.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzunapps.stocks.data.AlpavResponse
import com.zzunapps.stocks.data.StockItem
import com.zzunapps.stocks.network.AlphavantageService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockViewModel : ViewModel() {
    val allStocks = MutableLiveData<List<StockItem>>()

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://www.alphavantage.co/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(AlphavantageService::class.java)

        allStocks.value = mutableListOf<StockItem>()
        (allStocks.value as MutableList<StockItem>).add(StockItem("AAPL", 100.0))
        service.getStockData(
            mapOf(
                "function" to "TIME_SERIES_INTRADAY",
                "symbol" to "AAPL",
                "interval" to "1min",
                "apikey" to AlphavantageService.key
            )
        ).enqueue(object : retrofit2.Callback<AlpavResponse> {
            override fun onResponse(call: Call<AlpavResponse>, response: Response<AlpavResponse>) {
                val alphavObject = response.body()!!
                Log.d("api response", "${response.raw()}")
                Log.d("metaData", "${alphavObject.metaData}")
                Log.d("timeSeries", "${alphavObject.timeSeries}")
                val stockItem = StockItem(alphavObject.metaData.symbol, alphavObject.timeSeries.values.first().close)
                (allStocks.value as MutableList<StockItem>).add(stockItem)
            }

            override fun onFailure(call: Call<AlpavResponse>, t: Throwable) {

            }
        })
    }
}
