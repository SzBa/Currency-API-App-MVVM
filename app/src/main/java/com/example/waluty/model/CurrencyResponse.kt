package com.example.waluty.model

//data class do responsa API (nasze api jako response daje nr.partii, datę oraz listę walut)
data class CurrencyResponse(
    val no:String?,
    val effectiveDate:String?,
    val rates:List<Currency>?
)