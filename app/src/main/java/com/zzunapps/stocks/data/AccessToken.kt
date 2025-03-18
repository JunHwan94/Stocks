package com.zzunapps.stocks.data

import com.google.gson.annotations.SerializedName
import com.zzunapps.stocks.BuildConfig

data class AccessTokenRequestBody(
    @SerializedName("grant_type") val grantType: String = "client_credentials",
    @SerializedName("appkey") val appKey: String = BuildConfig.APP_KEY,
    @SerializedName("appsecret") val appSecret: String = BuildConfig.APP_SECRET
)

data class AccessTokenResponseBody(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("access_token_token_expired") val accessTokenExpired: String,
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("error_description") val errorMessage: String
)
