package com.zzunapps.stocks.features.widget.domain.usecase

import com.zzunapps.stocks.BuildConfig
import com.zzunapps.stocks.common.Constants.APP_KEY
import com.zzunapps.stocks.common.Constants.APP_SECRET
import com.zzunapps.stocks.common.Constants.AUTH
import com.zzunapps.stocks.common.Constants.AUTHORIZATION
import com.zzunapps.stocks.common.Constants.CONTENT_TYPE
import com.zzunapps.stocks.common.Constants.CONTENT_TYPE_APPLICATION_JSON
import com.zzunapps.stocks.common.Constants.EXCD
import com.zzunapps.stocks.common.Constants.SYMB
import com.zzunapps.stocks.common.Constants.TR_ID
import com.zzunapps.stocks.features.widget.data.remote.dto.OverseasPriceResponse
import com.zzunapps.stocks.data.remote.RetrofitClient
import retrofit2.Response

class GetStockDetailUseCase {
    suspend fun invoke(accessToken: String, pair: Pair<String, String>) : Response<OverseasPriceResponse> {
        return RetrofitClient.stockService.getStockData(
            mapOf(
                CONTENT_TYPE to CONTENT_TYPE_APPLICATION_JSON,
                AUTHORIZATION to "Bearer $accessToken",
                APP_KEY to BuildConfig.APP_KEY,
                APP_SECRET to BuildConfig.APP_SECRET,
                TR_ID to "HHDFS76200200"
            ),
            mapOf(
                AUTH to "", // 빈 값으로 보내야 함
                EXCD to pair.first, // 거래소 코드 NAS, NYS...
                SYMB to pair.second, // 종목 코드 AAPL, TRMD
            )
        )
    }
}