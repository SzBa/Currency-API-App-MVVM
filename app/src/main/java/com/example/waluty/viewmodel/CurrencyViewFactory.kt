package com.example.waluty.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waluty.model.CurrencyDataSource

class CurrencyViewFactory(private val repository:CurrencyDataSource): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository) as T
    }
}