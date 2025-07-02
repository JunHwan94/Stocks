package com.zzunapps.stocks.domain.usecase

import android.content.SharedPreferences
import android.icu.util.Calendar
import android.util.Log
import com.zzunapps.stocks.data.remote.dto.AccessTokenRequestBody
import com.zzunapps.stocks.data.remote.dto.AccessTokenResponseBody
import com.zzunapps.stocks.data.remote.RetrofitClient.authService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class CommonUseCase {
    private suspend fun requestAccessToken(processResponseBody: (AccessTokenResponseBody) -> Unit) {
        withContext(Dispatchers.IO) {
            val response = authService.getAccessToken(AccessTokenRequestBody())

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("requestAccessToken", "accessToken: ${response.body()?.accessToken}")
                    processResponseBody(body)
                }
            } else {
                Log.d("requestAccessToken", "request failed\n${response.raw()}")
                val errorBody = response.errorBody() as ResponseBody
                Log.d("requestAccessToken", "${errorBody.string()}")
                throw Exception("Error getting access token : ${response.code()}")
            }
        }
    }

    suspend fun checkAndUpdateAccessToken(tokenPrefs: SharedPreferences) {
        val savedAccessToken = tokenPrefs.getString("accessToken", "")
        val tokenIssuedAt = tokenPrefs.getLong("tokenIssuedAt", 0)
        val tokenHasExpired = Calendar.getInstance().timeInMillis - tokenIssuedAt >= 86400000

        Log.d("checkAndUpdateAccessToken", "tokenHasExpired: $tokenHasExpired")
        if (savedAccessToken == "" || tokenHasExpired) {
            requestAccessToken {
                tokenPrefs.edit().putString("accessToken", it.accessToken).apply()
                tokenPrefs.edit().putLong("tokenIssuedAt", Calendar.getInstance().timeInMillis)
                    .apply()
                Log.d("checkAndUpdateAccessToken", "accessToken: ${it.accessToken}")
            }
        } else {
            Log.d("checkAndUpdateAccessToken", "saved accessToken: ${savedAccessToken}")
        }
    }
}