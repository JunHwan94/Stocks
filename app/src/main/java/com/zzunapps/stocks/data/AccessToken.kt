package com.zzunapps.stocks.data

import com.google.gson.annotations.SerializedName
import com.zzunapps.stocks.BuildConfig

data class AccessTokenRequestBody(
    @SerializedName("grant_type") val grantType: String,
    @SerializedName("appkey") val appKey: String = BuildConfig.APP_KEY,
    @SerializedName("appsecret") val appSecret: String = BuildConfig.APP_SECRET
)

data class AccessTokenResponseBody(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String
)
