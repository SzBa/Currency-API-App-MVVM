package com.example.waluty.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waluty.model.ConcreteValue
import com.example.waluty.model.Currency
import com.example.waluty.model.DBHelper


class FavCurrenciesViewModel(context: Context):ViewModel() {
    private val _currency = MutableLiveData<List<ConcreteValue>>().apply { value = emptyList() }
    val currency: LiveData<List<ConcreteValue>> = _currency

    var changes=MutableLiveData<Boolean>()

    private val db = DBHelper(context)

    fun loadCurrencies() {
        _currency.value = db.allFavourite
    }
    fun removeFromFavourite(code:String,date:String)
    {
        db.deleteFavourite(code,date)
    }
    fun getSpecialCurrencyData(code:String)
    {
        _currency.value = db.neededCurrency(code)
    }
    fun addToFavourite(currency: ConcreteValue)
    {
        db.CheckIfFavIsAlreadyInDB(currency)
    }
    fun dbRecordChanged()
    {
        changes.value = changes.value !=true

    }
}