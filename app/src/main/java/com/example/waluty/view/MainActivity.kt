package com.example.waluty.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waluty.R
import com.example.waluty.model.Currency
import com.example.waluty.model.DBHelper
import com.example.waluty.model.Inject
import com.example.waluty.viewmodel.CurrencyViewFactory
import com.example.waluty.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //zadeklarowanie adapteru do recyclerView
    private lateinit var adapter: CurrencyAdapter
    private lateinit var viewModel: CurrencyViewModel
    private val dbConnect = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favourite-> OpenFavActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun OpenFavActivity() {
        val intent = Intent(this,FavCurrencyAct::class.java)
        ContextCompat.startActivity(this, intent, null)
    }

    private fun setupUI(){
        adapter = CurrencyAdapter(viewModel.currency.value?: emptyList(), viewModel.date.value?: "2020-01-01", this) //jezeli jest nullem to random
        recyclerView.adapter= adapter
        recyclerView.layoutManager= LinearLayoutManager(this)
    }

    private  fun setupViewModel(){
        viewModel = ViewModelProvider(this,CurrencyViewFactory(Inject.providerRepository())).get(CurrencyViewModel::class.java)
        viewModel.currency.observe(this,renderCurrencies)

        //Jezeli viewmodel sie zmienic wartosc w livedata srtring typu date to wywolaj funkcje checkdate()
        viewModel.date.observe(this,checkDate)

    }

    private val renderCurrencies= Observer<List<Currency>> {
        adapter.update(it)
    }

    //inicjuje recyclerview z pobranymi danymi
    private val checkDate = Observer<String> {
        dbConnect.CheckIfDataAlreadyInDBorNot(viewModel.currency.value!!,viewModel.date.value!!)
        setupUI()}


    override fun onResume() {
        super.onResume()
        viewModel.loadCurrencies()
    }
}
