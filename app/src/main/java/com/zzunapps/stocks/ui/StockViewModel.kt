package com.zzunapps.stocks.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzunapps.stocks.data.StockItem

class StockViewModel : ViewModel() {
    val allStocks = MutableLiveData<List<StockItem>>()

    init {

        allStocks.value = listOf()
    }
}
