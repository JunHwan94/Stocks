package com.zzunapps.stocks.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.zzunapps.stocks.R
import com.zzunapps.stocks.data.StockItem

class StocksWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Widget()
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
@GlanceComposable
fun Widget() {
    val data = Data.Builder()
        .put("data", "data") // todo : API 별로 worker 나눠서?
        .build()

    val workRequest = OneTimeWorkRequestBuilder<WidgetWorker>().setInputData(data).build()
    WorkManager.getInstance(LocalContext.current).enqueue(workRequest)

    val list = remember { mutableStateListOf<StockItem>() }

    Scaffold(
        titleBar = {
            TitleBar(
                startIcon = ImageProvider(R.drawable.baseline_attach_money_24),
                title = "Stocks",
                textColor = GlanceTheme.colors.onSurface
            )
        },
        modifier = GlanceModifier.fillMaxSize(),
        backgroundColor = GlanceTheme.colors.widgetBackground
    ) {
        LazyColumn {
            items(list) {
                Row {
                    Text(
                        text = it.stockName,
                        style = TextStyle(color = GlanceTheme.colors.onSurface)
                    )
                    Spacer(modifier = GlanceModifier.size(10.dp))
                    Text(
                        text = it.price.toString(),
                        style = TextStyle(color = GlanceTheme.colors.onSurface)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
@GlanceComposable
fun WidgetPreview(){
    GlanceTheme {
    }
}