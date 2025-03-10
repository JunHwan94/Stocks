package com.zzunapps.stocks.data

import com.google.gson.annotations.SerializedName

data class AlpavResponse(
    @SerializedName("Meta Data") val metaData: MetaData,
    @SerializedName("Time Series (1min)") val timeSeries: Map<String, PriceInfo>
)

data class MetaData(
    @SerializedName("2. Symbol") val symbol: String,
    @SerializedName("3. Last Refreshed") val lastRefreshed: String,
    @SerializedName("4. Interval") val interval: String,
)

data class PriceInfo(
    @SerializedName("4. close") val close: Double,
)