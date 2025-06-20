package com.zzunapps.stocks.ui.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zzunapps.stocks.BuildConfig
import com.zzunapps.stocks.data.Constants.AUTHORIZATION
import com.zzunapps.stocks.data.Constants.STOCK_ITEMS_JSON_KEY
import com.zzunapps.stocks.data.Constants.TR_ID
import com.zzunapps.stocks.data.Data
import com.zzunapps.stocks.data.StockItem
import com.zzunapps.stocks.network.RetrofitClient
import com.zzunapps.stocks.network.checkAndUpdateAccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class WidgetWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params){
    private val tokenPrefs = applicationContext.getSharedPreferences("token", Context.MODE_PRIVATE)

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val appWidgetId = inputData.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Log.e("WidgetWorker", "Invalid appWidgetId received.")
            return@withContext Result.failure()
        }

        val glanceId = GlanceAppWidgetManager(applicationContext).getGlanceIdBy(appWidgetId)
        if (glanceId == null) {
            Log.e("WidgetWorker", "Could not get GlanceId for appWidgetId: $appWidgetId")
            return@withContext Result.failure()
        }

        checkAndUpdateAccessToken(tokenPrefs)
        try {
            val accessToken = applicationContext.getSharedPreferences("token", Context.MODE_PRIVATE).getString("accessToken", "") ?: ""

            // todo : 종목별 요청
            val stockItems = requestOverseasPrice(accessToken)
            val stockItemsJson = Json.encodeToString(stockItems)

            updateAppWidgetState(
                applicationContext,
                PreferencesGlanceStateDefinition,
                glanceId
            ) { prefs ->
                prefs.toMutablePreferences().apply {
                    this[stringPreferencesKey(STOCK_ITEMS_JSON_KEY)] = stockItemsJson
                }
            }

            StocksWidget().updateAll(applicationContext)

            Log.d("MyWidgetWorker", "Widget state updated successfully for GlanceId: $glanceId")

            Result.success()
        } catch(e: Exception) {
            Log.d("WidgetWorker", "Error fetching data: ${e.message}")
            Result.failure()
        }
    }

    private suspend fun requestOverseasPrice(
        accessToken: String
    ): List<StockItem> {
        return withContext(Dispatchers.IO) {
            Data.stocks.map { pair ->
                val response = RetrofitClient.service.getStockData(
                    mapOf(
                        CONTENT_TYPE_KV,
                        AUTHORIZATION to "Bearer $accessToken",
                        APP_KEY_KV,
                        APP_SECRET_KV,
                        TR_ID to "HHDFS76200200"
                    ),
                    mapOf(
                        "AUTH" to "", // 빈 값으로 보내야 함
                        "EXCD" to pair.first, // 거래소 코드 NAS, NYS...
                        "SYMB" to pair.second, // 종목 코드 AAPL, TRMD
                    )
                )
                if (response.isSuccessful) {
                    val body = response.body() ?: throw Exception("Empty response body")
                    val symbol = body.priceDetail.rsym
                    val price = body.priceDetail.last
                    Log.d("WidgetWorker", "symbol: $symbol, price: $price")
                    StockItem(symbol.drop(4), price.dropLast(2))
                } else {
                    response.errorBody()
                    StockItem("", "")
                    throw Exception("Error getting overseas price : ${response.code()}")
                }
            }
        }
    }

    private companion object {
        private val CONTENT_TYPE_KV = "content-type" to "application/json; charset=utf-8"
        private val APP_KEY_KV = "appkey" to BuildConfig.APP_KEY
        private val APP_SECRET_KV = "appsecret" to BuildConfig.APP_SECRET
    }
}