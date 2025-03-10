package com.zzunapps.stocks.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.glance.text.TextStyle
import com.zzunapps.stocks.R
import com.zzunapps.stocks.data.StockItem
import com.zzunapps.stocks.ui.StockViewModel

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
    val stockViewModel = StockViewModel()
    val list = remember { mutableStateListOf<StockItem>() }
    stockViewModel.allStocks.observeForever {
        list.clear()
        list.addAll(it)
    }
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
        Widget()
    }
}