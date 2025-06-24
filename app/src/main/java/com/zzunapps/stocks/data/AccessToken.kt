package com.zzunapps.stocks.data

import com.zzunapps.stocks.BuildConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequestBody(
    @SerialName("grant_type") val grantType: String = "client_credentials",
    @SerialName("appkey") val appKey: String = BuildConfig.APP_KEY,
    @SerialName("appsecret") val appSecret: String = BuildConfig.APP_SECRET
)

@Serializable
data class AccessTokenResponseBody(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("access_token_token_expired") val accessTokenExpired: String,
    @SerialName("error_code") val errorCode: String = "",
    @SerialName("error_description") val errorMessage: String = ""
)
