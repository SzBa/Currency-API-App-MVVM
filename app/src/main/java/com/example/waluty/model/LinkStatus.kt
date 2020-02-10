package com.example.waluty.model

interface LinkStatus { //interface z funkcjami na temat połączenia
        fun onSuccess(obj:Any?)
        fun onError(obj:Any?)
}