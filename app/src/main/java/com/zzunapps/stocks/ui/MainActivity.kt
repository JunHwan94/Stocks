package com.zzunapps.stocks.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zzunapps.stocks.data.AlpavResponse
import com.zzunapps.stocks.network.AlphavantageService
import com.zzunapps.stocks.ui.theme.StocksTheme
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder().baseUrl("https://www.alphavantage.co/").addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(AlphavantageService::class.java)
        service.getStockData(
            mapOf(
                "function" to "TIME_SERIES_INTRADAY",
                "symbol" to "AAPL",
                "interval" to "1min",
                "apikey" to AlphavantageService.key
            )
        ).enqueue(object : retrofit2.Callback<AlpavResponse> {
            override fun onResponse(call: Call<AlpavResponse>, response: Response<AlpavResponse>) {
                Log.d("api response", "${response.raw()}")
            }

            override fun onFailure(call: Call<AlpavResponse>, t: Throwable) {
                Log.d("api response", t.message.toString())
            }
        })
        enableEdgeToEdge()
        setContent {
            StocksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StocksTheme {
        Greeting("Android")
    }
}