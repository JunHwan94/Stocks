package com.zzunapps.stocks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StockViewModel(stockName: String, price: Double) : ViewModel() {
    private val _stockName = MutableLiveData<String>("AAPL")
    private val _price = MutableLiveData<Double>(0.0)

    init {
        _stockName.value = stockName
        _price.value = price
    }

    val stockName: LiveData<String>
        get() {
            return _stockName
        }

    val price: LiveData<Double>
        get() {
            return _price
        }

    fun updateStockName(newStockName: String) {
        _stockName.value = newStockName
    }

    fun updatePrice(newPrice: Double) {
        _price.value = newPrice
    }
}
