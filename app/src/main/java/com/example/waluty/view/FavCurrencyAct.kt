package com.example.waluty.view

import android.R.attr.data
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waluty.R
import com.example.waluty.model.ConcreteValue
import com.example.waluty.viewmodel.FavCurrenciesFactory
import com.example.waluty.viewmodel.FavCurrenciesViewModel
import kotlinx.android.synthetic.main.activity_check_days.*


class FavCurrencyAct : AppCompatActivity() {

    private lateinit var adapter : DiffNeededCurrencyAdapter
    private lateinit var viewModel: FavCurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_days)

        setupViewModel()
        setupUI()

    }
    private fun setupUI(){
        adapter = DiffNeededCurrencyAdapter(viewModel.currency.value?: emptyList(),viewModel) //jezeli jest nullem to random
        recyclerViewCheck.adapter= adapter
        recyclerViewCheck.layoutManager= LinearLayoutManager(this)
    }

    private  fun setupViewModel(){
        viewModel = ViewModelProvider(this,FavCurrenciesFactory(this)).get(FavCurrenciesViewModel::class.java)
        viewModel.currency.observe(this,update)
        viewModel.changes.observe(this,update2)
    }
    private val update= Observer<List<ConcreteValue>> {
        adapter.update(it)
    }
    private val update2= Observer<Boolean> {
        viewModel.loadCurrencies()
        val toast = Toast.makeText(this, "Waluta zostala usunieta",Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCurrencies()
    }
}
