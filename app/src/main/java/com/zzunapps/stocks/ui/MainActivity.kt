package com.zzunapps.stocks.ui

import android.os.Bundle
import android.widget.Toast
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
import com.zzunapps.stocks.common.NetworkStatus
import com.zzunapps.stocks.data.remote.checkAndUpdateAccessToken
import com.zzunapps.stocks.ui.theme.StocksTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val tokenPrefs by lazy { applicationContext.getSharedPreferences("token", MODE_PRIVATE) }

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
        val connectivity = NetworkStatus.getConnectivityStatus(applicationContext)
        if(connectivity == NetworkStatus.TYPE_NOT_CONNECTED) {
            Toast.makeText(applicationContext, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            // 앱 최초 실행 시 액세스 토큰 발급 및 저장
            lifecycleScope.launch(Dispatchers.IO) {
                checkAndUpdateAccessToken(tokenPrefs)
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