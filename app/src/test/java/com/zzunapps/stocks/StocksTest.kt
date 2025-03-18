package com.zzunapps.stocks

import com.zzunapps.stocks.ui.StockViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StocksTest {
    val accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjQ0Y2RjMGM2LWIxMmMtNDE3MS1iODE1LWM2NzgzMmE2YmYyMyIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTc0MjM5MjI4OCwiaWF0IjoxNzQyMzA1ODg4LCJqdGkiOiJQU1dQN0EwaUR1dFoxWlR4eUtLS1RucGsyaUxMTVlVam9nVXAifQ.KaL8Qu2oZB2DBbecExQ-1d8j9fGZtO70fl00PPniMvm8rJycYXcrsMooL_0NzZkz2XHPVuLyKj1a1RZeRkGcmA"

    @Test
    fun overseasPriceTest() = runTest {
        val priceDetail = StockViewModel().getPriceDetail(
            accessToken = accessToken,
            excd = "NAS",
            symb = "TRMD"
        )
        assertEquals(priceDetail.rsym, "DNASTRMD")
    }

    @Test
    fun overseasPriceDifferentTest() = runTest {
        val priceDetail = StockViewModel().getPriceDetail(
            accessToken = accessToken,
            excd = "NAS",
            symb = "AAPL"
        )
        assertFalse(priceDetail.rsym == "DNASTRMD")
    }

    @Test
    fun accessTokenTest() = runTest {
        val accessToken = StockViewModel().getAccessToken()
        assert(accessToken.accessToken.isNotEmpty())
    }

    @Test
    fun accessTokenErrorTest() = runTest {
        val accessToken = StockViewModel().getAccessToken()
        assertEquals(accessToken.errorCode, "EGW00133")
    }
}