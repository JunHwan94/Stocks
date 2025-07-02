package com.zzunapps.stocks.data.remote.api

import com.zzunapps.stocks.data.remote.dto.AccessTokenRequestBody
import com.zzunapps.stocks.data.remote.dto.AccessTokenResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("oauth2/tokenP")
    suspend fun getAccessToken(@Body body: AccessTokenRequestBody): Response<AccessTokenResponseBody>
}