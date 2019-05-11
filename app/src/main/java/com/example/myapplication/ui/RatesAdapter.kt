package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RatesAdapter : RecyclerView.Adapter<RateViewHolder>() {

    private val rates: MutableList<Pair<String, Double>> = mutableListOf()

    fun setData(list: List<Pair<String, Double>>) {
        rates.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RateViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(rates[position])
    }

    override fun getItemCount(): Int = rates.size

}