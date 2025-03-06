package com.zzunapps.stocks.network

import com.zzunapps.stocks.data.AlpavResponse
import com.zzunapps.stocks.data.StockItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AlphavantageService {
    companion object {
        const val key = "8I0SPN8CQNTKNFG5"
    }
    //https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=AAPL&interval=1min&apikey=8I0SPN8CQNTKNFG5
    @GET("query")
    fun getStockData(@QueryMap options: Map<String, String>): Call<AlpavResponse>
}