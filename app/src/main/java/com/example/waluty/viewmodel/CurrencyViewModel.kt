package com.example.waluty.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waluty.model.*

class CurrencyViewModel(private val repository: CurrencyDataSource):ViewModel() {


    private val _currency = MutableLiveData<List<Currency>>().apply { value = emptyList() }
    val currency:LiveData<List<Currency>> = _currency

    private val _date = MutableLiveData<String>()
    val date:LiveData<String> = _date

    private val _isViewLoading=MutableLiveData<Boolean>()

    private val _onMessageError=MutableLiveData<Any>()

    private val pomocy1:MutableList<List<Currency>> = mutableListOf()

    private val _isEmptyList=MutableLiveData<Boolean>()

    fun loadCurrencies(){
        _isViewLoading.postValue(true)
        repository.retrieveCurrency(object:LinkStatus{
            override fun onError(obj: Any?) {
                _onMessageError.postValue(obj) //obiekt który tutaj otrzymuje jest wiadomością typu error zamiast tablicą json
                //a wiec onError(obj) dobrze działa, zrobić odpowiednie zmiany, żeby retrofit poprawnie pobierał dane i przypisywał do listy
                _isViewLoading.postValue(false)
            }

            override fun onSuccess(obj: Any?) {

                _isViewLoading.postValue(false)
                if(obj!=null && obj is List<*>){
                    if(obj.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _currency.value = obj as List<Currency>
                    }
                }
                if(obj!=null && obj is String)
                {
                    _date.value = obj
                }
            }
        })
    }

}