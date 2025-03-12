package com.zzunapps.stocks.network

import com.zzunapps.stocks.data.OverseasPriceResponse
import com.zzunapps.stocks.data.AccessTokenRequestBody
import com.zzunapps.stocks.data.AccessTokenResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface KISService {
    @POST("oauth2/tokenP")
    fun getAccessToken(@Body body: AccessTokenRequestBody): Call<AccessTokenResponseBody>

    @GET("uapi/overseas-price/v1/quotations/price-detail")
    fun getStockData(
        @HeaderMap headers: Map<String, String>,
        @QueryMap options: Map<String, String>
    ): Call<OverseasPriceResponse>
}