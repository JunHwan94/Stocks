package com.zzunapps.stocks.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
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
import androidx.glance.layout.width
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.zzunapps.stocks.R
import com.zzunapps.stocks.data.Constants.STOCK_ITEMS_JSON_KEY
import com.zzunapps.stocks.data.StockItem
import kotlinx.serialization.json.Json

class StocksWidget : GlanceAppWidget() {
    // 위젯의 상태 정의를 지정합니다.
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                WidgetContent()
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
@GlanceComposable
fun WidgetContent() {
    val prefs = currentState<Preferences>()

    val stockItemsJson = prefs[stringPreferencesKey(STOCK_ITEMS_JSON_KEY)] ?: "[]"
    val stockItems: List<StockItem> = remember(stockItemsJson) { // stockListJson이 변경될 때 변경됨
        try {
            Json.decodeFromString<List<StockItem>>(stockItemsJson)
        } catch(e: Exception) {
            emptyList()
        }
    } ?: emptyList()

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
            items(stockItems) {
                StockItemContent(it)
            }
        }
    }
}

@Composable
fun StockItemContent(item: StockItem) {
    Row {
        Text(
            modifier = GlanceModifier.width(40.dp),
            text = item.stockName,
            style = TextStyle(color = GlanceTheme.colors.onSurface)
        )
        Spacer(modifier = GlanceModifier.size(10.dp))
        Text(
            text = "$ ${item.price}",
            style = TextStyle(color = GlanceTheme.colors.onSurface)
        )
    }
}

@Preview
@Composable
@GlanceComposable
fun WidgetPreview(){
    GlanceTheme {
        WidgetContent()
    }
}