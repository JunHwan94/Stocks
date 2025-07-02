package com.zzunapps.stocks.features.widget.domain.model

import kotlinx.serialization.Serializable

@Serializable
class StockItem(val stockName: String, val price: String)