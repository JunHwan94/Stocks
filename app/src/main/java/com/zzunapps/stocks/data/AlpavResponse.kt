package com.zzunapps.stocks.data

import retrofit2.http.Field

data class AlpavResponse(
    @Field("Meta Data") val metaData: MetaData,
    @Field("Time Series (1min)") val timeSeries: Map<String, PriceInfo>
)

data class MetaData(
    @Field("2: Symbol") val symbol: String,
    @Field("3: Last Refreshed") val lastRefreshed: String,
    @Field("4: Interval") val interval: String
)

data class PriceInfo(
    @Field("4. close") val close: Double
)