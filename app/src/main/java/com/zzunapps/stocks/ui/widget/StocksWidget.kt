package com.zzunapps.stocks.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
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
import androidx.glance.currentState
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.zzunapps.stocks.R
import com.zzunapps.stocks.data.StockItem

class StocksWidget : GlanceAppWidget() {
    // 위젯의 상태 정의를 지정합니다.
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

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
    val prefs = currentState<Preferences>()

    val list = remember { mutableStateListOf<StockItem>() }

    // todo : WidgetWorker에서 결과 받으면 리스트 업데이트
    list.add(StockItem("AAPL", "100.0"))
    list.add(StockItem(prefs[stringPreferencesKey("symbol")] ?: "AAAA", prefs[stringPreferencesKey("price")] ?: "0.0"))

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
                StockItemContent(it)
            }
        }
    }
}

@Composable
fun StockItemContent(item: StockItem) {
    Row {
        Text(
            text = item.stockName,
            style = TextStyle(color = GlanceTheme.colors.onSurface)
        )
        Spacer(modifier = GlanceModifier.size(10.dp))
        Text(
            text = item.price,
            style = TextStyle(color = GlanceTheme.colors.onSurface)
        )
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