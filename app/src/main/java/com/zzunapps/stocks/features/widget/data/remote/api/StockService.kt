package com.zzunapps.stocks.features.widget.data.remote.api

import com.zzunapps.stocks.features.widget.data.remote.dto.OverseasPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface StockService {
    @GET("uapi/overseas-price/v1/quotations/price-detail")
    suspend fun getStockData(
        @HeaderMap headers: Map<String, String>,
        @QueryMap options: Map<String, String>
    ): Response<OverseasPriceResponse>
}