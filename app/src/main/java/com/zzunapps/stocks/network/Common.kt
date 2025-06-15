package com.zzunapps.stocks.network

import android.content.SharedPreferences
import android.icu.util.Calendar
import android.util.Log
import com.zzunapps.stocks.data.AccessTokenRequestBody
import com.zzunapps.stocks.data.AccessTokenResponseBody
import com.zzunapps.stocks.network.RetrofitClient.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun requestAccessToken(processResponseBody: (AccessTokenResponseBody) -> Unit) {
    withContext(Dispatchers.IO) {
        val response = service.getAccessToken(AccessTokenRequestBody())

        if(response.isSuccessful) {
            val body = response.body()
            if(body != null) {
                Log.d("MainActivity", "accessToken: ${response.body()?.accessToken}")
                processResponseBody(body)
            }
        } else {
            Log.d("MainActivity", "request failed")
            response.errorBody()
            throw Exception("Error getting access token : ${response.code()}")
        }
    }
}

suspend fun checkAndUpdateAccessToken(tokenPrefs: SharedPreferences) {
    val savedAccessToken = tokenPrefs.getString("accessToken", "")
    val tokenIssuedAt = tokenPrefs.getLong("tokenIssuedAt", 0)
    val tokenHasExpired = Calendar.getInstance().timeInMillis - tokenIssuedAt >= 86400000

    Log.d("MainActivity", "tokenHasExpired: $tokenHasExpired")
    if(savedAccessToken == "" || tokenHasExpired) {
        requestAccessToken {
            tokenPrefs.edit().putString("accessToken", it.accessToken).apply()
            tokenPrefs.edit().putLong("tokenIssuedAt", Calendar.getInstance().timeInMillis).apply()
            Log.d("MainActivity", "accessToken: ${it.accessToken}")
        }
    } else {
        Log.d("MainActivity", "saved accessToken: ${savedAccessToken}")
    }
}