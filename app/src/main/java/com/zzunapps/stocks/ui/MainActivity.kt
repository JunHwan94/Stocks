package com.zzunapps.stocks.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.icu.util.Calendar
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
import androidx.lifecycle.lifecycleScope
import com.zzunapps.stocks.network.RetrofitClient
import com.zzunapps.stocks.ui.theme.StocksTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        // 앱 최초 실행 시 액세스 토큰 발급 및 저장
        lifecycleScope.launch(Dispatchers.IO) {
            checkAndUpdateAccessToken(applicationContext)
        }
    }
}

suspend fun checkAndUpdateAccessToken(context: Context) {
    val savedAccessToken = context.getSharedPreferences("token", MODE_PRIVATE).getString("accessToken", "")
    val tokenIssuedAt = context.getSharedPreferences("token", MODE_PRIVATE).getLong("tokenIssuedAt", 0)
    val tokenHasNotExpired = Calendar.getInstance().timeInMillis - tokenIssuedAt < 86400000

    if(savedAccessToken == "" || !tokenHasNotExpired) {
        RetrofitClient.requestAccessToken {
            context.getSharedPreferences("token", MODE_PRIVATE).edit().putString("accessToken", it.accessToken).apply()
            context.getSharedPreferences("token", MODE_PRIVATE).edit().putLong("tokenIssuedAt", Calendar.getInstance().timeInMillis).apply()
            Log.d("MainActivity", "accessToken: ${it.accessToken}")
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