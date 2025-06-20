package com.zzunapps.stocks.data

import kotlinx.serialization.Serializable

@Serializable
class StockItem(val stockName: String, val price: String)