package com.example.waluty.model

object Inject{

    //CurrencyRepository może byc singletonem (tak na przyszłość)
    fun providerRepository():CurrencyDataSource{
        return CurrencyRepository()
    }
}