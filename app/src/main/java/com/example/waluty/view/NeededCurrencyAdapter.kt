package com.example.waluty.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waluty.R
import com.example.waluty.model.ConcreteValue
import com.example.waluty.viewmodel.FavCurrenciesViewModel

class NeededCurrencyAdapter(private var currency:List<ConcreteValue>,private var viewModel: FavCurrenciesViewModel):RecyclerView.Adapter<NeededCurrencyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currency.size
    }
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName:TextView = view.findViewById(R.id.currency_name)
        val textViewCurrencyZl:TextView = view.findViewById(R.id.currency_kurs)
        val textViewshortcut:TextView = view.findViewById(R.id.shortcut_check)
        val textViewDate:TextView = view.findViewById(R.id.date_check)
        val button:Button = view.findViewById(R.id.importantButton)
    }
    override fun onBindViewHolder(parent: MyViewHolder, position: Int) {
        val currency = currency[position]
        parent.textViewCurrencyZl.text = currency.mid.toString()
        parent.textViewshortcut.text = currency.code
        parent.textViewName.text = currency.name
        parent.textViewDate.text = currency.date
        parent.button.setOnClickListener {
            add(currency)
        }
    }
    fun update(data:List<ConcreteValue>){
        currency = data
        notifyDataSetChanged()
    }
    fun add(code:ConcreteValue) = viewModel.addToFavourite(code)
}