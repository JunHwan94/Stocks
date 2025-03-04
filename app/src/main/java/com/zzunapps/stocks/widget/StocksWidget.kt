package com.zzunapps.stocks.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
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
import com.zzunapps.stocks.R
import com.zzunapps.stocks.viewmodel.StockViewModel

class StocksWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Widget()
            }
        }
    }
}

@Composable
@GlanceComposable
fun Widget() {
    val list = listOf(StockViewModel("AAPL", 100.0), StockViewModel("GOOG", 200.0))
    Scaffold(
        titleBar = {
            TitleBar(
                startIcon = ImageProvider(R.drawable.baseline_attach_money_24),
                title = "Stocks"
            )
        },
        modifier = GlanceModifier.fillMaxSize()
    ) {
        LazyColumn {
            items(list) {
                Row {
                    Text(it.stockName.value ?: "")
                    Spacer(modifier = GlanceModifier.size(10.dp))
                    Text(it.price.value.toString())
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
        Widget()
    }
}