package com.zzunapps.stocks

import com.zzunapps.stocks.ui.StockViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StocksTest {
    val accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b2tlbiIsImF1ZCI6IjE2NjdjYmM4LTI3MjYtNDljOC1hNWMzLTk2YjVkOWNhMGRjYiIsInByZHRfY2QiOiIiLCJpc3MiOiJ1bm9ndyIsImV4cCI6MTc0MjE5Mzg1MiwiaWF0IjoxNzQyMTA3NDUyLCJqdGkiOiJQU1dQN0EwaUR1dFoxWlR4eUtLS1RucGsyaUxMTVlVam9nVXAifQ.GKovOadGs7Fq1UJCR5Q9klPfTTJbBbyqaiww1uIUmZ_liys3L1vM56aW9RQ09U-3liMRZyTTNNb0_cc4itfITw"

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
}