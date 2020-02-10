package com.example.waluty.model

interface CurrencyDataSource {
    fun retrieveCurrency(callback: LinkStatus)
}