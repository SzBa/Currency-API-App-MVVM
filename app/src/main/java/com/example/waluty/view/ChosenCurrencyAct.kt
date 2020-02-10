package com.example.waluty.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waluty.R
import com.example.waluty.model.ConcreteValue
import com.example.waluty.model.DBHelper
import com.example.waluty.model.Inject
import com.example.waluty.viewmodel.CurrencyViewFactory
import com.example.waluty.viewmodel.CurrencyViewModel
import com.example.waluty.viewmodel.FavCurrenciesFactory
import com.example.waluty.viewmodel.FavCurrenciesViewModel
import kotlinx.android.synthetic.main.activity_check_days.*

class ChosenCurrencyAct : AppCompatActivity() {
    private lateinit var adapter : NeededCurrencyAdapter
    private lateinit var viewModel:FavCurrenciesViewModel
    private var code:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_days)

        setupViewModel()
        setupUI()

        val intent = intent
        code = intent.getStringExtra("code")

    }
    private fun setupUI(){
        adapter = NeededCurrencyAdapter(viewModel.currency.value?: emptyList(),viewModel)
        recyclerViewCheck.adapter= adapter
        recyclerViewCheck.layoutManager= LinearLayoutManager(this)
    }

    private  fun setupViewModel(){
        viewModel = ViewModelProvider(this, FavCurrenciesFactory(this)).get(FavCurrenciesViewModel::class.java)
        viewModel.currency.observe(this,update)
    }
    private val update= Observer<List<ConcreteValue>> {
        adapter.update(it)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSpecialCurrencyData(code)
    }
}
